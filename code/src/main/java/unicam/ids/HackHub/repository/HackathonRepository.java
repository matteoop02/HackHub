package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Hackathon;
import java.util.List;
import java.util.Optional;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    public boolean existsByName(String name);

    Optional<Hackathon> findHackathonByName(String name);
    List<Hackathon> findAllByIsPublic(Boolean isPublic);
}
