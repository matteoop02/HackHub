package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unicam.ids.HackHub.enums.SubmissionStatus;
import unicam.ids.HackHub.model.Submission;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    @Query("SELECT s FROM Submission s WHERE s.title = :title")
    Optional<Submission> findByTitle(@Param("name") String name);

    @Query("SELECT s FROM Submission s WHERE s.title = :title AND s.state <> :state")
    Optional<Submission> findByTitleAndStateIsNot(@Param("title") String title, @Param("state") SubmissionStatus state);

    @Query("SELECT s FROM Submission s WHERE s.teamName = :teamName")
    List<Submission> findByTeamName(@Param("teamName") String teamName);

    @Query("SELECT s FROM Submission s WHERE s.hackathonName = :hackathonName")
    List<Submission> findByHackathonName(@Param("hackathonName") String hackathonName);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Submission s WHERE s.title = :title")
    Boolean existsSubmissionByTitle(@Param("title") String title);

    @Query("SELECT s FROM Submission s WHERE s.teamName = :teamName AND s.hackathonName = :hackathonName AND s.state <> :state")
    Optional<Submission> findByTeamNameAndHackathonNameAndStateIsNot(@Param("name") String name, @Param("hackathonName") String hackathonName, @Param("state") SubmissionStatus state);

    @Query("SELECT s FROM Submission s WHERE s.teamName = :teamName AND s.hackathonName = :hackathonName")
    Optional<Submission> findByTeamNameAndHackathonName(@Param("teamName") String teamName, @Param("hackathonName") String hackathonName);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Submission s WHERE s.teamName = :teamName AND s.hackathonName = :hackathonName")
    boolean existsSubmissionByTeamNameAndHackathonName(@Param("teamName") String teamName, @Param("hackathonName") String hackathonName);
}
