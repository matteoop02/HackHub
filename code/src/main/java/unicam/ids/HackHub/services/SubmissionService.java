package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.enums.SubmissionState;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.repository.SubmissionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

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
}
