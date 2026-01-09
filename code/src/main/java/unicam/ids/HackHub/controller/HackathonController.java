package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.ComplexDTO.TeamSubscriptionRequest;
import unicam.ids.HackHub.dto.requests.CreateHackathonRequest;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.service.HackathonService;
import unicam.ids.HackHub.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/hackathon")
@Tag(name = "Hackathon", description = "Gestione degli hackathon")
public class HackathonController {
    @Autowired
    private HackathonService hackathonService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ApiResponse(responseCode = "200", description = "Utente registrato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida o dati mancanti")
    public ResponseEntity<String> createHackathon(@RequestBody CreateHackathonRequest createHackathonRequest) {
        try {
            Hackathon hackathon = hackathonService.createHackathon(createHackathonRequest);
            return ResponseEntity.ok("Hackathon creato");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/consultation")
    public ResponseEntity<List<Hackathon>> getHackathonsList(@RequestBody String username) {
        try {
            if (userService.existsUserByUsername(username))
                return ResponseEntity.ok(hackathonService.getHackathons());

            return ResponseEntity.ok(hackathonService.getHackathonsPublic());
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signTeam")
    public ResponseEntity<String> signTeam(@RequestBody TeamSubscriptionRequest teamSubscriptionRequest) {
        try {
            hackathonService.signTeamToHackathon(teamSubscriptionRequest.getUsername(), teamSubscriptionRequest.getHackathonName());
            return ResponseEntity.ok("Team iscritto all'hackathon");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}