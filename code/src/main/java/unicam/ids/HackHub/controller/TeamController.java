package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.TeamDTO;
import unicam.ids.HackHub.dto.UserDTO;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.services.TeamService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HackathonRepository hackathonRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTeam(@RequestBody TeamDTO teamDTO, UserDTO userDTO) {
        try {
            Optional<User> creator = userRepository.findByEmail(userDTO.getEmail());
            if(creator.isEmpty()) {
                throw new IllegalArgumentException("Utente non trovato");
            }
            Optional<Hackathon> hackathon = hackathonRepository.findById(teamDTO.getHackathonId());
            if(hackathon.isEmpty()) {
                throw new IllegalArgumentException("Hakcathon non trovato");
            }
            List<User> members = userRepository.findAllById(teamDTO.getMembersIds());
            Team team = teamService.createTeam(creator.get(), teamDTO.getName(), hackathon.get(), members);
            return ResponseEntity.ok(team);
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
