package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.enums.HackathonStatus;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

  @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Hackathon h WHERE h.name = :name")
  boolean existsByName(@Param("name") String name);

  @Query("SELECT h FROM Hackathon h WHERE LOWER(h.name) = LOWER(:name)")
  Optional<Hackathon> findHackathonByName(@Param("name") String name);

  @Query("SELECT h FROM Hackathon h WHERE h.id = :id")
  Optional<Hackathon> findHackathonById(@Param("id") int id);

  @Query("SELECT h FROM Hackathon h WHERE h.isPublic = :isPublic")
  List<Hackathon> findAllByIsPublic(@Param("isPublic") Boolean isPublic);

  @Query("SELECT h FROM Hackathon h WHERE h.id = :id AND h.isPublic = :isPublic")
  Optional<Hackathon> findHackathonByIdAndIsPublic(@Param("id") int id, @Param("isPublic") boolean isPublic);

  @Query("SELECT h FROM Hackathon h WHERE LOWER(h.name) = LOWER(:name) AND h.isPublic = :isPublic")
  Optional<Hackathon> findHackathonByNameAndIsPublic(@Param("hackathonName") String hackathonName,
      @Param("isPublic") boolean isPublic);

  @Query("SELECT h FROM Hackathon h WHERE h.organizer = :organizer")
  List<Hackathon> findHackathonByOrganizer(@Param("organizer") User organizer);

  @Query("SELECT h FROM Hackathon h WHERE h.judge = :judge")
  List<Hackathon> findHackathonByJudge(@Param("judge") User judge);

  @Query("SELECT h FROM Hackathon h JOIN h.mentors m WHERE m = :mentor")
  List<Hackathon> findHackathonByMentors(@Param("mentor") User user);

  List<Hackathon> findByStateAndStartDateLessThanEqual(HackathonStatus state, LocalDateTime now);

  List<Hackathon> findByStateAndEndDateLessThanEqual(HackathonStatus state, LocalDateTime now);
}
