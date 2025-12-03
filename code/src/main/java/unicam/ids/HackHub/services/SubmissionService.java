package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.model.Submission;
import unicam.ids.HackHub.repository.SubmissionRepository;
import java.util.List;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByHackathon(Long hackathonId) {
        return submissionRepository.findByHackathonId(hackathonId);
    }
}
