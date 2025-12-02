package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // Controlla se un utente è già in un team
    boolean existsByMembersContains(User user, Hackathon hackathon);

    //Verifica un team appartenente ad un determinato hackathon ha un determinato nome
    boolean existByName(String name, Hackathon hackathon);
}
