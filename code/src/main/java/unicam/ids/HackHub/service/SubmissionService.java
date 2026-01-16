package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.requests.CreateTeamSubmissionRequest;
import unicam.ids.HackHub.dto.requests.HackathonSubmissionEvaluationRequest;
import unicam.ids.HackHub.dto.requests.UpdateTeamSubmissionRequest;
import unicam.ids.HackHub.enums.HackathonStatus;
import unicam.ids.HackHub.enums.SubmissionStatus;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.SubmissionRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private HackathonService hackathonService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByHackathonName(String name) { return submissionRepository.findByHackathonName(name); }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByTeamName(String name) {
        return submissionRepository.findByTeamName(name);
    }

    @Transactional(readOnly = true)
    public Submission getSubmissionsByTeamNameAndHackathonNameAndStateIsNot(String name, String hackathonName, SubmissionStatus state) {
        return submissionRepository.findByTeamNameAndHackathonNameAndStateIsNot(name, hackathonName, state)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found"));
    }

    public boolean existsSubmissionByTeamNameAndHackathonName(String teamName, String hackathonName) {
        return submissionRepository.existsSubmissionByTeamNameAndHackathonName(teamName, hackathonName);
    }

    @Transactional
    public void evaluateHackathonSubmission(HackathonSubmissionEvaluationRequest request) {
        Hackathon hackathon = hackathonService.findHackathonByName(request.hackathonName());
        Team team = teamService.findByName(request.teamName());

        if(hackathon.getState().equals(HackathonStatus.IN_VALUTAZIONE))
            throw new IllegalArgumentException("Hackathon non in valutazione");

        Submission submission = getSubmissionsByTeamNameAndHackathonNameAndStateIsNot(team.getName(), hackathon.getName(), SubmissionStatus.VALUTATA);

        submission.setScore(request.score());
        submission.setComment(request.comment());
        submission.setState(SubmissionStatus.VALUTATA);
        submissionRepository.save(submission);
    }

    public void createSubmission(Authentication authentication, CreateTeamSubmissionRequest request) {
        User user = userService.findUserByUsername(authentication.getName());
        Team team = teamService.findByName(user.getTeam().getName());

        //Controllo se esiste una sottomissione per un dato team e hackathon
        if (existsSubmissionByTeamNameAndHackathonName(team.getName(), team.getHackathon().getName()))
            throw new IllegalArgumentException("Sottomissione già esistente per " + team.getName() + "e " + team.getHackathon().getName());

        //Se hackathon non in iscrizione non posso creare la sottomissione
        if (!team.getHackathon().getState().equals(HackathonStatus.IN_ISCRIZIONE))
            throw new IllegalArgumentException("Hackathon non in iscrizione");

        Submission submission = Submission.builder()
                .title(request.title())
                .content(request.content())
                .sendingDate(LocalDateTime.now())
                .lastEdit(LocalDateTime.now())
                .state(SubmissionStatus.INVIATA)
                .team(team)
                .hackathon(team.getHackathon())
                .build();

        submissionRepository.save(submission);
    }

    @Transactional
    public void updateSubmission(UpdateTeamSubmissionRequest request) {
        User user = userService.findUserByUsername(request.authentication().getName());
        Team team = teamService.findByName(user.getTeam().getName());

        //Cerco la Submission attuale ovvero quella che appartiene all'hackathon attuale
        Submission submission = getSubmissionsByTeamNameAndHackathonNameAndStateIsNot(team.getName(), team.getHackathon().getName(), SubmissionStatus.VALUTATA);

        submission.setTitle(request.title());   //Aggiorno il titolo
        submission.setContent(request.content());   //Aggiorno il contenuto
        submission.setLastEdit(LocalDateTime.now());    //Aggiorno ultima modifica

        submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByStaffMember(String staffUsername) {
        User staff = userService.findUserByUsername(staffUsername);
        List<Hackathon> hackathons = hackathonService.getHackathonsByUser(staff);

        List<Submission> submissions = new ArrayList<>();

        if (!hackathons.isEmpty()) {
            for (int i = 0; i < (hackathons.size() - 1); i++) {
                submissions.addAll(getSubmissionsByHackathonName(hackathons.get(i).getName()));
            }
        }

        return submissions;
    }
}
