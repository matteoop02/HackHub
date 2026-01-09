package unicam.ids.HackHub.service;

import unicam.ids.HackHub.enums.InviteState;
import unicam.ids.HackHub.model.*;
import unicam.ids.HackHub.factory.InviteFactory;
import unicam.ids.HackHub.repository.OutsideInviteRepository;
import unicam.ids.HackHub.repository.InsideInviteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InviteService {

    private final InviteFactory inviteFactory;
    private final OutsideInviteRepository outsideInviteRepository;
    private final InsideInviteRepository teamInviteRepository;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final TeamService teamService;

    @Transactional
    public InviteOutsidePlatform inviteOutsideUser(
            String senderName,
            String recipientEmail,
            String message) {

        log.info("Invitando utente esterno: {}", recipientEmail);

        // Verifica se esiste già un invito pendente per questa email
        if (outsideInviteRepository.existsByRecipientEmailAndStatus(
                recipientEmail, InviteState.PENDING)) {
            throw new IllegalStateException("Esiste già un invito pendente per questa email");
        }

        InviteOutsidePlatform invite = inviteFactory.createOutsideInvite(
                senderName, recipientEmail, message);

        invite.send();
        InviteOutsidePlatform saved = outsideInviteRepository.save(invite);

        emailService.sendEmail(recipientEmail, senderName, message);

        return saved;
    }

    @Transactional
    public InviteInsidePlatform inviteUserToTeam(
            String senderUsername,
            String recipientUsername,
            String teamName,
            UserRole proposedRole,
            String message) {

        // Verifica che non ci sia già un invito pendente
        if (teamInviteRepository.existsByRecipientUsernameAndTeamNameAndStatus(
                recipientUsername, teamName, InviteState.PENDING)) {
            throw new IllegalStateException("Esiste già un invito pendente per questo utente");
        }

        InviteInsidePlatform invite = inviteFactory.createTeamInvite(
                senderUsername, recipientUsername, teamName, proposedRole, message);

        invite.send();
        InviteInsidePlatform saved = teamInviteRepository.save(invite);

        notificationService.sendTeamInviteNotification(saved);

        return saved;
    }

    @Transactional
    public void acceptOutsideInvite(String token) {
        InviteOutsidePlatform invite = outsideInviteRepository.findByInviteToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invito non trovato"));

        invite.accept();
        outsideInviteRepository.save(invite);

        log.info("Invito esterno accettato: {}", invite.getId());
    }

    @Transactional
    public void acceptTeamInvite(Long inviteId, String username) {
        InviteInsidePlatform invite = teamInviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invito non trovato"));

        if (!invite.getRecipientUsername().equals(username)) {
            throw new IllegalArgumentException("Non sei autorizzato ad accettare questo invito");
        }

        invite.accept();
        teamInviteRepository.save(invite);

        // Aggiungi l'utente al team
        teamService.addMemberToTeam(invite.getTeamName(), username);

        notificationService.notifyInviteAccepted(invite);

        log.info("Invito al team accettato: {}", invite.getId());
    }

    @Transactional
    public void rejectInvite(Long inviteId, String username) {
        InviteInsidePlatform invite = teamInviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invito non trovato"));

        if (!invite.getRecipientUsername().equals(username)) {
            throw new IllegalArgumentException("Non sei autorizzato a rifiutare questo invito");
        }

        invite.reject();
        teamInviteRepository.save(invite);

        notificationService.notifyInviteRejected(invite);

        log.info("Invito rifiutato: {}", invite.getId());
    }

    @Transactional
    public void cancelInvite(Long inviteId, String username) {
        InviteInsidePlatform invite = teamInviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invito non trovato"));

        if (!invite.getSenderUsername().equals(username)) {
            throw new IllegalArgumentException("Non sei autorizzato a cancellare questo invito");
        }

        invite.cancel();
        teamInviteRepository.save(invite);

        log.info("Invito cancellato: {}", invite.getId());
    }

    @Transactional(readOnly = true)
    public List<InviteInsidePlatform> getPendingInvitesForUser(String username) {
        return teamInviteRepository.findByRecipientUsernameAndStatus(username, InviteState.PENDING);
    }

    @Transactional(readOnly = true)
    public List<InviteInsidePlatform> getTeamInvites(String teamName) {
        return teamInviteRepository.findByTeamNameAndStatus(teamName, InviteState.PENDING);
    }
}