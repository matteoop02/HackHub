package unicam.ids.HackHub.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.TeamRepository;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Crea un nuovo team
    public Team createTeam(User creator, String name, List<User> members) {
        // Controllo ruolo
        String roleName = creator.getRole().getName();
        if (roleName.equals("MENTORE") || roleName.equals("GIUDICE") || roleName.equals("ORGANIZZATORE")) {
            throw new IllegalArgumentException("Utente dello staff non può creare un team");
        }

        // Controllo se già in un team
        boolean isAlreadyInTeam = teamRepository.existsByMembersContains(creator);
        if (isAlreadyInTeam) {
            throw new IllegalArgumentException("Utente già membro di un team");
        }

        // Crea team e aggiungi il creatore come primo membro
        Team team = new Team();
        team.setName(name);
        members.add(creator);
        team.setMembers(members);

        return teamRepository.save(team);
    }
}
