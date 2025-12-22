package unicam.ids.HackHub.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.TeamRepository;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Crea un nuovo team
    public Team createTeam(User creator, String name, Hackathon hackathon, List<User> members) {

        String roleCategory = creator.getRole().getCategory();
        if (roleCategory.equals("STAFF"))
            throw new IllegalArgumentException("Membro dello staff non può creare un team");

        boolean isAlreadyInTeam = teamRepository.existsByMembersContainsAndHackathon(creator, hackathon);
        if (isAlreadyInTeam)
            throw new IllegalArgumentException("Utente già membro di un team");

        boolean isNameUsed = teamRepository.existsByNameAndHackathon(name, hackathon);
        if(isNameUsed)
            throw new IllegalArgumentException("Nome team già in uso");

        //Creazione nuovo team
        Team team = new Team();
        team.setName(name);
        team.setHackathon(hackathon);
        team.setTeamLeader(creator);
        members.add(creator);
        team.setMembers(members);

        //Salvataggio nuovo team
        return teamRepository.save(team);
    }
}
