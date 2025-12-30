package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.dto.ComplexDTO.HackathonSubmissionsEvaluationDTO;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.enums.SubmissionState;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.SubmissionRepository;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private HackathonRepository hackathonRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByHackathonId(Long hackathonId) {
        return submissionRepository.findByHackathonId(hackathonId);
    }

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByTeamId(Long teamId) {
        return submissionRepository.findByTeamId(teamId);
    }

    @Transactional
    public void updateSubmission(Long id, String title, String content, Date sendingDate, Date lastEdit) {
        Optional<Submission> submission = submissionRepository.findById(id);
        if(submission.isEmpty()) {
            throw new IllegalArgumentException("Sottomissione non trovata");
        }
        if(submission.get().getState() == SubmissionState.VALUTATA) {
            throw new IllegalArgumentException("Sottomissione già valutata");
        }
        submission.get().setTitle(title);
        submission.get().setContent(content);
        submission.get().setSendingDate(sendingDate);
        submission.get().setLastEdit(lastEdit);
    }

    @Transactional
    public void evaluateHackathonSubmissions(Long judgeId, Long hackathonId, Map<Long, HackathonSubmissionsEvaluationDTO.EvaluationMapValue> judgements) {
        Optional<User> judge = userRepository.findById(judgeId);
        if(judge.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        if(userService.isType(judge.get(), "giudice")) {
            throw new IllegalArgumentException("Utente non autorizzato");
        }
        Optional<Hackathon> hackathon = hackathonRepository.findById(hackathonId);
        if(hackathon.isEmpty()) {
            throw new IllegalArgumentException("Hackathon non trovato");
        }
        if(hackathon.get().getState().equals(HackathonState.IN_VALUTAZIONE)) {
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
            Optional<Team> team = teamRepository.findById(value.getTeamId());
            if(team.isEmpty()) {
                throw new IllegalArgumentException("Team non trovato");
            }
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
