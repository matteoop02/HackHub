package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Hackathon;
import java.util.List;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {
    public boolean existsByName(String name);
    public List<Hackathon> findByPlace(String place);
    public List<Hackathon> findByState(HackathonState state);
}
