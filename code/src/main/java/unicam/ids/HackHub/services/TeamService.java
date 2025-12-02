package unicam.ids.HackHub.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Crea un nuovo team
    public Team createTeam(String name, User creator, Hackathon hackathon) {

        String roleName = creator.getRole().getName();
        if (roleName.equals("MENTORE") || roleName.equals("GIUDICE") || roleName.equals("ORGANIZZATORE")) {
            throw new IllegalArgumentException("Utente dello staff non può creare un team");
        }

        boolean isAlreadyInTeam = teamRepository.existsByMembersContains(creator, hackathon);
        if (isAlreadyInTeam) {
            throw new IllegalArgumentException("Utente già membro di un team");
        }

        boolean isNameUsed = teamRepository.existByName(name, hackathon);
        if(isNameUsed) {
            throw new IllegalArgumentException("Nome team già in uso");
        }

        Team team = new Team(name, creator, hackathon);
        return teamRepository.save(team);
    }
}
