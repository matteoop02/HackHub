package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Team t JOIN t.members m WHERE m = :user")
    boolean existsByMembersContains(@Param("user") User user);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Team t WHERE t.name = :name")
    boolean existsByName(@Param("name") String name);

    @Query("SELECT t FROM Team t WHERE t.name = :teamName")
    Optional<Team> findByName(@Param("teamName") String teamName);
}
