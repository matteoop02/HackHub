package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.ComplexDTO.HackathonSubmissionsEvaluationDTO;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.enums.SubmissionState;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.SubmissionRepository;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;

import java.util.*;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HackathonService hackathonService;

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByHackathonName(String name) { return submissionRepository.findByHackathonName(name); }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByTeamName(String name) {
        return submissionRepository.findByTeamName(name);
    }

    @Transactional
    public void updateSubmission(String title, String content, Date sendingDate, Date lastEdit) {
        Submission submission = submissionRepository.findByTitleAndStateIsNot(title, SubmissionState.VALUTATA)
                .orElseGet(Submission::new);

        if (Objects.equals(submission.getTitle(), title))
            throw new IllegalArgumentException("Sottomissione già esistente!");

        submission.setTitle(title);
        submission.setContent(content);
        submission.setSendingDate(sendingDate);
        submission.setLastEdit(lastEdit);

        submissionRepository.save(submission);
    }

    @Transactional
    public void evaluateHackathonSubmissions(String username, String hackathonName, Map<Long, HackathonSubmissionsEvaluationDTO.EvaluationMapValue> judgements) {
        User user = userService.findUserByUsername(username);

        Hackathon hackathon = hackathonService.findHackathonByName(hackathonName);

        if(hackathon.getState().equals(HackathonState.IN_VALUTAZIONE)) {
            throw new IllegalArgumentException("Hackathon non in valutazione");
        }
        for (Long submissionId : judgements.keySet()) {
            HackathonSubmissionsEvaluationDTO.EvaluationMapValue value = judgements.get(submissionId);
            Optional<Submission> submission = submissionRepository.findById(submissionId);
            if(submission.isEmpty()) {
                throw new IllegalArgumentException("Sottomissione non trovata");
            }
            if(submission.get().getState().equals(SubmissionState.VALUTATA)) {
                throw new IllegalArgumentException("Sottomissione già valutata");
            }
            //Team team = TeamService.findTeamByTeamName(value.get());

            Double score = value.getScore();
            if(score > 10.0 || score < 0.0) {
                throw new IllegalArgumentException("Voto non valido");
            }
            String comment = value.getComment();
            submission.get().setScore(score);
            submission.get().setComment(comment);
            submission.get().setState(SubmissionState.VALUTATA);
            submissionRepository.save(submission.get());
        }
    }
}
