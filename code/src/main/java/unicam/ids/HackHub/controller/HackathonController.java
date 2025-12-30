package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.ComplexDTO.HackathonSubmissionsEvaluationDTO;
import unicam.ids.HackHub.dto.ComplexDTO.TeamSubscriptionDTO;
import unicam.ids.HackHub.dto.ComplexDTO.UserHackathonDTO;
import unicam.ids.HackHub.dto.HackathonDTO;
import unicam.ids.HackHub.dto.UserDTO;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.services.HackathonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hackathon")
public class HackathonController {
    @Autowired
    private HackathonService hackathonService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createHackathon(@RequestBody UserHackathonDTO userHackathonDTO) {
        try {
            Optional<User> creator = userRepository.findByEmail(userHackathonDTO.getUserDTO().getEmail());
            if(creator.isEmpty()) {
                throw new IllegalArgumentException("Utente non trovato");
            }
            if(!creator.get().getRole().getName().toString().equalsIgnoreCase("organizzatore")) {
                throw new IllegalArgumentException("Solo l'organizzatore può creare un hackathon");
            }
            Hackathon hackathon = hackathonService.createHackathon(creator.get(), userHackathonDTO.getHackathonDTO().getName(), userHackathonDTO.getHackathonDTO().getPlace(), userHackathonDTO.getHackathonDTO().getRegulation(), userHackathonDTO.getHackathonDTO().getSubscriptionDeadline(), userHackathonDTO.getHackathonDTO().getStartDate(), userHackathonDTO.getHackathonDTO().getEndDate(), userHackathonDTO.getHackathonDTO().getReward(), userHackathonDTO.getHackathonDTO().getMaxTeamSize());
            return ResponseEntity.ok("Hackathon creato");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/consultation")
    public ResponseEntity<List<HackathonDTO>> getHackathonsList(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
                return ResponseEntity.ok(hackathonService.getPublicHackathons());
            }
            return ResponseEntity.ok(hackathonService.getAllHackathonsAsDTO());
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signTeam")
    public ResponseEntity<String> signTeam(@RequestBody TeamSubscriptionDTO iscrizioneTeamDTO) {
        try {
            hackathonService.signTeamToHackathon(iscrizioneTeamDTO.getUserId(), iscrizioneTeamDTO.getHackathonId(), iscrizioneTeamDTO.getTeamId());
            return ResponseEntity.ok("Team iscritto all'hackathon");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}