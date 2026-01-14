package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unicam.ids.HackHub.model.Hackathon;
import java.util.List;
import java.util.Optional;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    boolean existsByName(String name);

    @Query("SELECT h FROM Hackathon h WHERE LOWER(h.name) = LOWER(:name)")
    Optional<Hackathon> findHackathonByName(@Param("name") String name);

    List<Hackathon> findAllByIsPublic(Boolean isPublic);

    Optional<Hackathon> findHackathonByNameAndIsPublic(String hackathonName, boolean isPublic);
}
