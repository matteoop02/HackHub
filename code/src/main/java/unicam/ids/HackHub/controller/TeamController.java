package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.requests.CreateTeamRequest;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HackathonRepository hackathonRepository;

    @PostMapping("/createTeam")
    @Operation(
            summary = "Creazione team",
            description = "Permette a un utente registrato di creare un team",
            requestBody = @RequestBody(
                    description = "Team creato dall'utente",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio creazione team",
                                    value = """
                                    {
                                      "CreateTeamRequest": {
                                        "username": "matteop",
                                        "name": "Borlotti",
                                        "hackathonName": "Scienza",
                                        "submissionTitle": "Nuove Scoperte"                                 
                                    }
                                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Team creato con successo")
    @ApiResponse(responseCode = "401", description = "Team non creato, dati non validi")
    public ResponseEntity<String> createTeam(@RequestBody CreateTeamRequest createTeamRequest) {
        try {
            teamService.createTeam(createTeamRequest);
            return ResponseEntity.ok("Team creato con successo");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
