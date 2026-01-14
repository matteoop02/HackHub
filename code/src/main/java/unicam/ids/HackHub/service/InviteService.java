package unicam.ids.HackHub.service;

import org.springframework.security.core.Authentication;
import unicam.ids.HackHub.dto.requests.InsideInviteRequest;
import unicam.ids.HackHub.dto.requests.OutsideInviteRequest;
import unicam.ids.HackHub.dto.requests.RegisterFromInviteRequest;
import unicam.ids.HackHub.enums.InviteState;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.*;
import unicam.ids.HackHub.factory.InviteFactory;
import unicam.ids.HackHub.repository.OutsideInviteRepository;
import unicam.ids.HackHub.repository.InsideInviteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.enums.InviteState;
import unicam.ids.HackHub.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InviteService {

    private final InviteFactory inviteFactory;
    private final OutsideInviteRepository outsideInviteRepository;
    private final InsideInviteRepository insideInviteRepository;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final TeamService teamService;
    private final UserService userService;
    private final UserRoleService userRoleService;

    @Transactional
    public InviteOutsidePlatform inviteOutsideUser(Authentication authentication, OutsideInviteRequest outsideInviteRequest) {
        if(existsBySenderUsernameAndRecipientEmailAndStatus(
                userService.findUserByUsername(authentication.getName()),
                outsideInviteRequest.recipientEmail(),
                InviteState.PENDING))
            throw new IllegalStateException("Esiste già un invito pendente per " + outsideInviteRequest.recipientEmail() + "da parte di " + authentication.getName());

        User senderUser = userService.findUserByUsername(authentication.getName());
        InviteOutsidePlatform invite = inviteFactory.createOutsideInvite(
                senderUser, outsideInviteRequest.recipientEmail(), outsideInviteRequest.message());

        invite.send();
        InviteOutsidePlatform saved = outsideInviteRepository.save(invite);

        emailService.sendEmail(outsideInviteRequest.recipientEmail(), senderUser.getEmail(), "Invito da HackHub",outsideInviteRequest.message());

        return saved;
    }

//    @Transactional
//    public void acceptOutsideInvite(String token) {
//        InviteOutsidePlatform invite = findInviteByToken(token);
//
//        invite.accept();
//        outsideInviteRepository.save(invite);
//    }

    @Transactional
    public void acceptInviteAndRegisterUser(RegisterFromInviteRequest request) {

        InviteOutsidePlatform invite = findInviteByToken(request.token());

        if (invite.isExpired())
            throw new IllegalStateException("L'invito è scaduto");

        if (invite.getStatus() != InviteState.PENDING)
            throw new IllegalStateException("Invito non più valido");

        //Crea utente sulla piattaforma
        User user = User.builder()
                .email(invite.getRecipientEmail())
                .name(request.name())
                .surname(request.surname())
                .password(request.password())
                .role(userRoleService.getDefaultUserRole())
                .build();

        userService.save(user);

        invite.accept();
        outsideInviteRepository.save(invite);
    }

    @Transactional
    public void rejectOutsideInvite(String token) {
        InviteOutsidePlatform invite = findInviteByToken(token);

        invite.reject();
        outsideInviteRepository.save(invite);
    }

    @Transactional
    public void cancelOutsideInvite(String token) {
        InviteOutsidePlatform invite = findInviteByToken(token);

        invite.cancel();
        outsideInviteRepository.save(invite);
    }

    //------------------------------- INSIDE INVITE MANAGE -------------------------------

    @Transactional
    public InviteInsidePlatform inviteUserToTeam(InsideInviteRequest insideInviteRequest) {
        //Carico gli utenti
        User senderUser = userService.findUserByUsername(insideInviteRequest.senderUsername());
        User recipientUser = userService.findUserByUsername(insideInviteRequest.recipientUsername());

        // Verifica che non ci sia già un invito pendente per un dato utente da un dato team
        if (existsByRecipientUserAndTeamAndStatus(recipientUser, senderUser.getTeam(), InviteState.PENDING))
            throw new IllegalStateException("Esiste già un invito pendente per questo utente");

        //Carico il ruolo
        UserRole proposedRole = userRoleService.findUserRoleById(insideInviteRequest.proposedRoleId());

        InviteInsidePlatform invite = inviteFactory.createTeamInvite(senderUser, recipientUser, proposedRole, insideInviteRequest.message());

        invite.send();
        InviteInsidePlatform saved = insideInviteRepository.save(invite);

        notificationService.sendTeamInviteNotification(saved);
        return saved;
    }

    @Transactional
    public void acceptTeamInvite(Authentication authentication, Long inviteId) {
        InviteInsidePlatform invite = findInviteById(inviteId);

        if (!invite.getRecipientUser().getUsername().equals(authentication.getName())) {
            throw new IllegalArgumentException("Non sei autorizzato ad accettare questo invito");
        }

        invite.accept();
        insideInviteRepository.save(invite);

        // Aggiungi l'utente al team
        teamService.addMemberToTeam(invite.getTeam(), userService.findUserByUsername(authentication.getName()), invite.getProposedRole());
        notificationService.notifyInviteAccepted(invite);
    }

    @Transactional
    public void rejectTeamInvite(Authentication authentication, Long inviteId) {
        InviteInsidePlatform invite = findInviteById(inviteId);

        if (!invite.getRecipientUser().getUsername().equals(authentication.getName()))
            throw new IllegalArgumentException("Non sei autorizzato a rifiutare questo invito");

        invite.reject();
        insideInviteRepository.save(invite);
        notificationService.notifyInviteRejected(invite);
    }

    @Transactional
    public void cancelTeamInvite(Authentication authentication, Long inviteId) {
        InviteInsidePlatform invite = findInviteById(inviteId);

        if (!invite.getSenderUser().getUsername().equals(authentication.getName()))
            throw new IllegalArgumentException("Non sei autorizzato a cancellare questo invito");

        invite.cancel();
        insideInviteRepository.save(invite);
    }

    //------------------------------- FIND INVITE -------------------------------

    @Transactional(readOnly = true)
    public List<InviteInsidePlatform> findPendingInvitesForUser(Authentication authentication) {
        return insideInviteRepository.findByRecipientUserAndStatus(userService.findUserByUsername(authentication.getName()), InviteState.PENDING);
    }

    @Transactional(readOnly = true)
    public List<InviteInsidePlatform> findTeamInvites(Authentication authentication) {
        Team team = teamService.findByName(userService.findUserByUsername(authentication.getName()).getTeam().getName());
        return insideInviteRepository.findByTeamAndStatus(team, InviteState.PENDING);
    }

    public List<Invite> findEmailInvites(String email) {
        // inside invites
        List<InviteInsidePlatform> inside = insideInviteRepository
                .findByRecipientUserAndStatus(userService.findUserByEmail(email), InviteState.PENDING);

        // outside invites
        List<InviteOutsidePlatform> outside = outsideInviteRepository
                .findByRecipientEmailAndStatus(email, InviteState.PENDING);

        List<Invite> allInvites = new ArrayList<>();
        allInvites.addAll(inside);
        allInvites.addAll(outside);

        return allInvites;
    }

    //------------------------------- UTILITY -------------------------------

    @Transactional
    public InviteInsidePlatform findInviteById(Long inviteId) {
        return insideInviteRepository.findById(inviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Invito non trovato"));
    }

    @Transactional
    public InviteOutsidePlatform findInviteByToken(String token) {
        return outsideInviteRepository.findByInviteToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invito non trovato"));
    }

    @Transactional
    public boolean existsByRecipientUserAndTeamAndStatus(User recipientUser, Team team, InviteState inviteState) {
        return insideInviteRepository.existsByRecipientUserAndTeamAndStatus(recipientUser, team, inviteState);
    }

    @Transactional
    public boolean existsBySenderUsernameAndRecipientEmailAndStatus(User senderUser, String recipientEmail, InviteState inviteState) {
        return outsideInviteRepository.existsBySenderUserAndRecipientEmailAndStatus(senderUser, recipientEmail, inviteState);
    }
}