package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.enums.SubmissionState;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByTitle(String name);
    Optional<Submission> findByTitleAndStateIsNot(String title, SubmissionState state);
    List<Submission> findByTeamName(String teamName);
    List<Submission> findByHackathonName(String hackathonName);

    Boolean existsSubmissionByTitle(String title);
}
