package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.SubmissionState;
import unicam.ids.HackHub.model.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByTitle(String name);
    Optional<Submission> findByTitleAndStateIsNot(String title, SubmissionState state);
    List<Submission> findByTeamName(String teamName);
    List<Submission> findByHackathonName(String hackathonName);

    Boolean existsSubmissionByTitle(String title);

    Optional<Submission> findByTeamNameAndHackathonNameAndStateIsNot(String name, String hackathonName, SubmissionState state);

    boolean existsSubmissionByTeamNameAndHackathonName(String teamName, String hackathonName);

    Optional<Submission> findByTeamNameAndHackathonName(String name, String name1);
}
