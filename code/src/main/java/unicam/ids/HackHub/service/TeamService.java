package unicam.ids.HackHub.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import unicam.ids.HackHub.dto.requests.CreateTeamRequest;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.SubmissionRepository;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;
    private UserService userService;
    private HackathonService hackathonService;

    // Crea un nuovo team
    @Transactional
    public void createTeam(CreateTeamRequest createTeamRequest) {
        //Controllo se l'utente esiste
        User user = userService.findUserByUsername(createTeamRequest.getUsername());
        //Controllo se l'hackathon esiste
        Hackathon hackathon = hackathonService.getHackathonByName(createTeamRequest.getHackathonName());

        boolean isAlreadyInTeam = teamRepository.existsByMembersContainsAndHackathon(user, hackathon);
        if (isAlreadyInTeam)
            throw new IllegalArgumentException("Utente già membro di un team");

        String name = createTeamRequest.getName();
        boolean isNameUsed = teamRepository.existsByNameAndHackathon(name, hackathon);
        if(isNameUsed)
            throw new IllegalArgumentException("Nome team già in uso");

        Team team = new Team();
        team.setName(name);
        team.setHackathon(hackathon);
        team.setTeamLeader(user);
        team.addMember(user);

        teamRepository.save(team);
    }

    @Transactional
    public void addMemberToTeam(String teamName, String username) {
        //Controllo se l'utente esiste
        User user = userService.findUserByUsername(username);

        //Controllo se il team esiste
        Team team = teamRepository.findByName(teamName)
                .orElseThrow(() -> new ResourceNotFoundException("Team non trovato"));

        // Controlla se l'utente è già nel team
        if (team.getMembers().contains(user)) {
            throw new IllegalArgumentException("Utente è già nel team");
        }

        team.getMembers().add(user); // aggiungi l'utente
        teamRepository.save(team);   // salva il team
    }

    public Team findByName(String teamName) {
        return teamRepository.findByName(teamName)
                .orElseThrow(() -> new ResourceNotFoundException("Team non trovato"));
    }
}
