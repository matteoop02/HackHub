package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByMembersContainsAndHackathon(User user, Hackathon hackathon);
    boolean existsByNameAndHackathon(String name, Hackathon hackathon);
}
