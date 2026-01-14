package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.requests.CreateTeamRequest;
import unicam.ids.HackHub.service.TeamService;


@RestController
@RequestMapping("/api/team")
@Tag(name = "Team", description = "Gestione dinamiche dei team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/utente/create")
    @Operation(
            summary = "Creazione nuovo team",
            description = "Permette la registrazione di un nuovo team da parte di un Utente"
    )
    @ApiResponse(responseCode = "200", description = "Team registrato con successo")
    @ApiResponse(responseCode = "400", description = "Team non creato")
    public ResponseEntity<String> createTeam(Authentication authentication, @RequestBody @Valid CreateTeamRequest  createTeamRequest) {
        try {
            teamService.createTeam(authentication, createTeamRequest);
            return ResponseEntity.ok("Team creato con successo");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/utente/joinToTeam")
    @Operation(
            summary = "Aggiunge l'utente loggato al team se pubblico",
            description = """
        Un utente può unirsi a un team pubblico.
    """
    )
    @ApiResponse(responseCode = "200", description = "Partecipazione avvenuta con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella richiesta o dati non validi")
    public ResponseEntity<String> joinToTeam(Authentication authentication, @RequestParam @Valid String teamName) {
        try {
            teamService.joinToTeam(authentication, teamName);
            return ResponseEntity.ok("Unione al team effettuata con successo");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/leaderDelTeam/remove")
    @Operation(
            summary = "Rimuovi membro dal team",
            description = """
        Rimuove un membro del team.
    """
    )
    @ApiResponse(responseCode = "200", description = "Rimozione avvenuta con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella richiesta o dati non validi")
    public ResponseEntity<String> removeMemberToTeam(Authentication authentication, @RequestParam @Valid String member) {
        try {
            teamService.deleteMemberToTeam(authentication, member);
            return ResponseEntity.ok("Membro rimosso con successo");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/membroDelTeam/leaveTeam")
    @Operation(
            summary = "Rimuove l'utente loggato dal team",
            description = """
        Un utente può lasciare il team.
    """
    )
    @ApiResponse(responseCode = "200", description = "Partecipazione avvenuta con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella richiesta o dati non validi")
    public ResponseEntity<String> leaveTeam(Authentication authentication, @RequestParam @Valid String teamName) {
        try {
            teamService.leaveTeam(authentication, teamName);
            return ResponseEntity.ok("Membro rimosso con successo");
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
