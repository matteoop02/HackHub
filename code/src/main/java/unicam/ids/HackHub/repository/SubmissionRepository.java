package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    public List<Submission> findByHackathonId(Long hackathonId);
}
