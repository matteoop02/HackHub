package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    public Optional<Submission> findById(Long id);
    public List<Submission> findByHackathonId(Long hackathonId);
    public List<Submission> findByTeamId(Long teamId);
}
