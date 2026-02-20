package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.requests.hackathon.*;
import unicam.ids.HackHub.dto.responses.PaymentStatusResponse;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.exceptions.*;
import unicam.ids.HackHub.factory.HackathonStateFactory;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.model.declareWinner.WinnerStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private EmailService emailService;

    // ----------------------- GET -----------------------

    @Transactional(readOnly = true)
    public List<Hackathon> getHackathons() {
        return hackathonRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Hackathon> getHackathonsByUser(User user) {
        List<Hackathon> hackathons = new ArrayList<>();

        if (user.getRole().getId() == 6L)
            hackathons = hackathonRepository.findHackathonByOrganizer(user);
        else if (user.getRole().getId() == 5L)
            hackathons = hackathonRepository.findHackathonByJudge(user);
        else if (user.getRole().getId() == 4L)
            hackathons = hackathonRepository.findHackathonByMentors(user);

        return hackathons;
    }

    @Transactional(readOnly = true)
    public List<Hackathon> getHackathonsPublic() {
        return hackathonRepository.findAllByIsPublic(true);
    }

    // ----------------------- CREATE HACKATHON -----------------------

    @Transactional
    public void createHackathon(Authentication authentication, CreateHackathonRequest request) {

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

        unicam.ids.HackHub.model.state.HackathonState hackathonState = HackathonStateFactory.from(hackathon.getState());
        hackathonState.signTeam(hackathon, team);

        save(hackathon);
    }

    public void unsubscribeTeamToHackathon(Authentication authentication) {

        User leader = userService.findUserByUsername(authentication.getName());
        Team team = teamService.findByName(leader.getTeam().getName());
        Hackathon hackathon = findHackathonByName(team.getHackathon().getName());
        unicam.ids.HackHub.model.state.HackathonState hackathonState = HackathonStateFactory.from(hackathon.getState());
        hackathonState.unsubscribeTeamToHackathon(hackathon, team);
        save(hackathon);
    }

    // ----------------------- FIND -----------------------

    public Hackathon findHackathonByName(String hackathonName) {
        String normalized = hackathonName.trim().toLowerCase();
        return hackathonRepository.findAll().stream()
                .filter(h -> h.getName().trim().toLowerCase().equals(normalized))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    public Hackathon findHackathonById(int id) {
        return hackathonRepository.findAll().stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    @Transactional(readOnly = true)
    public Hackathon findHackathonInfo(int id) {
        return findHackathonById(id);
    }

    public Hackathon findPublicHackathonInfo(int id) {
        return hackathonRepository.findHackathonByIdAndIsPublic(id, true)
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    public Hackathon findHackathonByIdAndIsPublic(int id) {
        return hackathonRepository.findAll().stream()
                .filter(h -> h.getId() == id)
                .filter(Hackathon::getIsPublic)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Hackathon non trovato"));
    }

    public List<Submission> getHackathonSubmissions(Authentication authentication) {
        return submissionService.getSubmissionsByHackathonName(authentication.getName());
    }

    // ----------------------- PAYMENT -----------------------

    @Transactional(readOnly = true)
    public PaymentStatusResponse verifyHackathonPricePayment(String hackathonName) {
        Hackathon hackathon = findHackathonByName(hackathonName);
        return paymentService.verifyPaymentForHackathon(hackathon);
    }

    // ----------------------- HELPER -----------------------

    @Transactional
    public void setJudge(SetJudgeToHackathonRequest setJudgeToHackathonRequest) {
        Hackathon hackathon = findHackathonByName(setJudgeToHackathonRequest.hackathonName());
        hackathon.setJudge(userService.findUserByUsername(setJudgeToHackathonRequest.judgeUsername()));
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void removeJudge(RemoveJudgeFromHackathonRequest removeJudgeFromHackathonRequest) {
        Hackathon hackathon = findHackathonByName(removeJudgeFromHackathonRequest.hackathonName());
        if (hackathon.getJudge() == null) {
            throw new IllegalArgumentException("Nessun giudice assegnato a questo hackathon");
        }
        hackathon.setJudge(null);
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void addMentor(AddMentorToHackathonRequest addMentorToHackathonRequest) {
        Hackathon hackathon = findHackathonByName(addMentorToHackathonRequest.hackathonName());
        hackathon.getMentors().add(userService.findUserByUsername(addMentorToHackathonRequest.mentorUsername()));
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void removeMentor(RemoveMentorFromHackathonRequest removeMentorFromHackathonRequest) {
        Hackathon hackathon = findHackathonByName(removeMentorFromHackathonRequest.hackathonName());
        hackathon.getMentors().remove(userService.findUserByUsername(removeMentorFromHackathonRequest.mentorUsername()));
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void updateStartDate(UpdateHackathonStartAndEndDateRequest request) {
        Hackathon hackathon = findHackathonByName(request.hackathonName());

        LocalDateTime newStart = request.startDate();
        LocalDateTime newEnd;

        if (request.endDate() != null) {
            newEnd = request.endDate();
        } else {
            Duration diff = Duration.between(hackathon.getStartDate(), hackathon.getEndDate());
            newEnd = newStart.plus(diff);
        }

        hackathon.setStartDate(newStart);
        hackathon.setEndDate(newEnd);

        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void declareWinner(String hackathonName, WinnerStrategy strategy) {
        Hackathon hackathon = findHackathonByName(hackathonName);
        List<Submission> submissions = submissionService.getSubmissionsByHackathonName(hackathon.getName());
        unicam.ids.HackHub.model.state.HackathonState hackathonState = HackathonStateFactory.from(hackathon.getState());
        hackathonState.declareWinner(hackathon, submissions, strategy);
        save(hackathon);
        paymentService.payWinner(hackathon, hackathon.getOrganizer());
    }

    public void save(Hackathon hackathon) {
        hackathonRepository.save(hackathon);
    }

    public void saveAll(List<Hackathon> hackathons) { hackathonRepository.saveAll(hackathons); }
}