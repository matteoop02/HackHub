package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.requests.CreateHackathonRequest;
import unicam.ids.HackHub.dto.requests.SignTeamRequest;
import unicam.ids.HackHub.dto.requests.UpdateHackathonStartDateRequest;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.exceptions.*;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private SubmissionService submissionService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PaymentService paymentService;

    // ----------------------- GET -----------------------

    @Transactional(readOnly = true)
    public List<Hackathon> getHackathons() {
        return hackathonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Hackathon> getHackathonsPublic() {
        return hackathonRepository.findAllByIsPublic(true);
    }

    // ----------------------- CREATE HACKATHON -----------------------

    @Transactional
    public void createHackathon(Authentication authentication, CreateHackathonRequest request) {

        // Controllo duplicati nome
        if (hackathonRepository.existsByName(request.name().trim()))
            throw new DuplicateHackathonException("Esiste già un hackathon con lo stesso nome");

        User organizer = userService.findUserByUsername(authentication.getName());

        Hackathon hackathon = Hackathon.builder()
                .name(request.name().trim())
                .place(request.place())
                .regulation(request.regulation())
                .subscriptionDeadline(request.subscriptionDeadline())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .reward(request.reward())
                .maxTeamSize(request.maxTeamSize())
                .isPublic(request.isPublic())
                .state(HackathonState.IN_ISCRIZIONE)
                .organizer(organizer)
                .build();

        save(hackathon);
    }

    // ----------------------- SIGN/UNSUBSCRIBE TEAM TO HACKATHON -----------------------

    @Transactional
    public void signTeamToHackathon(Authentication authentication, SignTeamRequest request) {

        User leader = userService.findUserByUsername(authentication.getName());
        Team team = leader.getTeam();

        if (team == null)
            throw new TeamNotAssignedException("L'utente non appartiene a nessun team");

        Hackathon hackathon = findHackathonByName(request.hackathonName());

        validateTeamForSignUp(team, hackathon);

        // Associa team all'hackathon
        team.setHackathon(hackathon);
        hackathon.getTeams().add(team);

        // Salvataggio: cascade salva anche i team
        save(hackathon);
    }

    public void unsubscribeTeamToHackathon(Authentication authentication) {

        User leader = userService.findUserByUsername(authentication.getName());
        Team team = teamService.findByName(leader.getTeam().getName());
        Hackathon hackathon = findHackathonByName(team.getHackathon().getName());

        hackathon.getTeams().remove(team);
        save(hackathon);
    }

    // ----------------------- VALIDAZIONE TEAM -----------------------

    private void validateTeamForSignUp(Team team, Hackathon hackathon) {

        if (hackathon.getState() != HackathonState.IN_ISCRIZIONE)
            throw new HackathonClosedException("Hackathon non aperto alle iscrizioni");

        if (team.getHackathon() != null)
            throw new TeamAlreadyRegisteredException("Il team è già iscritto a un hackathon");

        if (team.getMembers().size() > hackathon.getMaxTeamSize())
            throw new TeamTooLargeException("Numero membri superiore al massimo consentito");

        //In teoria non serve se aggiorniamo dinamicamente lo stato dell'hackathon
        if (LocalDateTime.now().isAfter(hackathon.getSubscriptionDeadline()))
            throw new HackathonClosedException("Scadenza iscrizioni superata");
    }

    // ----------------------- FIND -----------------------

    public Hackathon findHackathonByName(String hackathonName) {
        String normalized = hackathonName.trim().toLowerCase();
        return hackathonRepository.findAll().stream()
                .filter(h -> h.getName().trim().toLowerCase().equals(normalized))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    @Transactional(readOnly = true)
    public Hackathon findHackathonInfo(String hackathonName) {
        return findHackathonByName(hackathonName);
    }

    public Hackathon findPublicHackathonInfo(String hackathonName) {
        return hackathonRepository.findHackathonByNameAndIsPublic(hackathonName, true)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    public List<Submission> getHackathonSubmissions(Authentication authentication) {
        return submissionService.getSubmissionsByHackathonName(authentication.getName());
    }

    // ----------------------- HELPER -----------------------

    @Transactional
    public void setJudge(String hackathonName, String judgeUsername) {
        Hackathon hackathon = findHackathonByName(hackathonName);
        hackathon.setJudge(userService.findUserByUsername(judgeUsername));
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void addMentor(String hackathonName, String mentorUsername) {
        Hackathon hackathon = findHackathonByName(hackathonName);
        hackathon.getMentors().add(userService.findUserByUsername(mentorUsername));
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void removeMentor(String hackathonName, String mentorUsername) {
        Hackathon hackathon = findHackathonByName(hackathonName);
        hackathon.getMentors().remove(userService.findUserByUsername(mentorUsername));
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void updateStartDate(UpdateHackathonStartDateRequest request) {
        Hackathon hackathon = findHackathonByName(request.hackathonName());

        LocalDateTime newStart = request.startDate();
        LocalDateTime newEnd;

        if (request.endDate() != null) {
            // Usa il valore fornito dal client
            newEnd = request.endDate();
        } else {
            // Mantiene la differenza tra start e end
            Duration diff = Duration.between(hackathon.getStartDate(), hackathon.getEndDate());
            newEnd = newStart.plus(diff);
        }

        // Validazioni (forse si può togliere perche uso @ChronologicalDates)
        if (!newStart.isBefore(newEnd)) {
            throw new IllegalArgumentException("La data di inizio deve essere antecedente alla data di fine");
        }

        hackathon.setStartDate(newStart);
        hackathon.setEndDate(newEnd);

        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void declareWinner(String teamName) {
        Team team = teamService.findByName(teamName);
        Hackathon hackathon = findHackathonByName(team.getHackathon().getName());

        if (hackathon.getState() != HackathonState.CONCLUSO)
            throw new IllegalArgumentException("Hackathon non concluso, impossibile proclamare il vincitore!");

        hackathon.setTeamWinner(team);
        save(hackathon);

        // Eroga premio automaticamente
        paymentService.payWinner(hackathon, hackathon.getOrganizer());
    }

    public void save(Hackathon hackathon) {
        hackathonRepository.save(hackathon);
    }
}


