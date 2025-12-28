package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.HackathonDTO;
import unicam.ids.HackHub.dto.TeamDTO;
import unicam.ids.HackHub.dto.UserDTO;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
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
    public ResponseEntity<?> createHackathon(@RequestBody HackathonDTO hackathonDTO, UserDTO userDTO) {
        try {
            Optional<User> creator = userRepository.findByEmail(userDTO.getEmail());
            if(creator.isEmpty()) {
                throw new IllegalArgumentException("Utente non trovato");
            }
            if(creator.get().getRole().toString().equalsIgnoreCase("organizzatore")) {
                throw new IllegalArgumentException("Solo l'organizzatore può creare un hackathon");
            }
            Hackathon hackathon = hackathonService.createHackathon(creator.get(), hackathonDTO.getName(), hackathonDTO.getPlace(), hackathonDTO.getRegulation(), hackathonDTO.getSubscriptionDeadline(), hackathonDTO.getStartDate(), hackathonDTO.getEndDate(), hackathonDTO.getReward(), hackathonDTO.getMaxTeamSize());
            return ResponseEntity.ok(hackathon);
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
}