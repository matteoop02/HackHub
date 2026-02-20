package unicam.ids.HackHub.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import unicam.ids.HackHub.dto.requests.invite.*;
import unicam.ids.HackHub.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import unicam.ids.HackHub.service.InviteService;
import java.util.List;

@RestController
@RequestMapping("/api/invites")
@Tag(name = "Inviti", description = "Gestione degli inviti: Interni (da e verso i team) ed Esterni (per utenti invitati ad iscriversi alla piattaforma)")
public class InviteController {

    @Autowired
    private InviteService inviteService;

    //------------------------------- OUTSIDE INVITE MANAGE -------------------------------

    @PostMapping("/public/inviteOutsideUser")
    @Operation(
            summary = "Invia invito esterno",
            description = "Permette a un utente di invitarne uno non registrato alla piattaforma.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio di invio invito.",
                                    value = """
                                {
                                  "recipientEmail": "matteo.fagnani@studenti.unicam.it",
                                  "message": "qualcosa",
                                }
                                """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Invito inviato con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella richiesta o dati non validi")
    public ResponseEntity<InviteOutsidePlatform> inviteOutsideUser(Authentication authentication, @Valid @RequestBody OutsideInviteRequest outsideInviteRequest) {
        try{
            InviteOutsidePlatform invite = inviteService.inviteOutsideUser(authentication, outsideInviteRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(invite);

        }catch (Exception ex){
            return  ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/outside/acceptInviteAndRegisterUser")
    @Operation(
        summary = "Accetta invito esterno e registra l'utente",
        description = "Permette a un utente invitato via email di completare la registrazione usando il token dell’invito.",
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        examples = @ExampleObject(
                                name = "Esempio registrazione da invito",
                                value = """
                                {
                                  "token": "abc123TOKENabc123",
                                  "username": "andrea.pistolesi",
                                  "name": "Andrea",
                                  "surname": "Pistolesi",
                                  "password": "123456789",
                                  "dateOfBirth": "2000-03-02"
                                }
                                """
                        )
                )
        )
    )
    @ApiResponse(responseCode = "200", description = "Invito e registrazione avvenuti con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella richiesta o dati non validi")
    public ResponseEntity<String> acceptInviteAndRegisterUser(@RequestBody @Valid RegisterFromInviteRequest request) {
        try {
            inviteService.acceptInviteAndRegisterUser(request);
            return ResponseEntity.ok("Registrazione completata! Utente aggiunto alla piattaforma.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/outside/rejectOutsideInvite")
    @Operation(
            summary = "Rifiuta invito esterno.",
            description = "Permette a un utente di rifiutare l’invito esterno.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio di rifiuto.",
                                    value = """
                                {
                                  "token": "abc123TOKENabc123",
                                }
                                """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Rifiuto avvenuto con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella richiesta o dati non validi")
    public ResponseEntity<String> rejectOutsideInvite(@RequestBody @Valid RejectOutsideInviteRequest rejectOutsideInviteRequest) {
        try{
            inviteService.rejectOutsideInvite(rejectOutsideInviteRequest);
            return ResponseEntity.ok("Rifiuto dell'invito riuscito!");
        } catch (Exception ex){
            return ResponseEntity.badRequest().body("Rifiuto dell'invito fallito!" + ex.getMessage());
        }
    }

    //------------------------------- INSIDE INVITE MANAGE -------------------------------

    @PostMapping("/leaderDelTeam/inviteToTeam")
    public ResponseEntity<InviteInsidePlatform> inviteToTeam(@RequestBody @Valid InsideInviteRequest insideInviteRequest) {

        InviteInsidePlatform invite = inviteService.inviteUserToTeam(insideInviteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(invite);
    }

    @PostMapping("/inviteManage/acceptTeamInvite")
    public ResponseEntity<String> acceptTeamInvite(Authentication authentication, @RequestBody @Valid AcceptTeamInvite acceptTeamInvite) {
        try {
            inviteService.acceptTeamInvite(authentication, acceptTeamInvite);
            return ResponseEntity.ok("Invito accettato con successo!");
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body("Accettazione dell'invito fallita!\n" + ex.getMessage());
        }
    }

    @PostMapping("/inviteManage/rejectTeamInvite")
    public ResponseEntity<String> rejectTeamInvite(Authentication authentication, @RequestParam Long inviteId) {
        try {
            inviteService.rejectTeamInvite(authentication, inviteId);
            return ResponseEntity.ok("Invito accettato con successo!");
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body("Accettazione dell'invito fallita!\n" + ex.getMessage());
        }
    }

    //------------------------------- GET INVITE -------------------------------

    @GetMapping("/inviteManage/senderEmail")
    public ResponseEntity<List<InviteInsidePlatform>> findPendingInvitesForEmails(Authentication authentication) {

        List<InviteInsidePlatform> invites = inviteService.findPendingInvitesForUser(authentication);
        return ResponseEntity.ok(invites);
    }

    @GetMapping("/inviteManage/user")
    public ResponseEntity<List<InviteInsidePlatform>> findAllPendingInvitesForUsers(Authentication authentication) {

        List<InviteInsidePlatform> invites = inviteService.findPendingInvitesForUser(authentication);
        return ResponseEntity.ok(invites);
    }

    @GetMapping("/inviteManage/team")
    public ResponseEntity<List<InviteInsidePlatform>> findAllTeamInvites(Authentication authentication) {
        List<InviteInsidePlatform> invites = inviteService.findTeamInvites(authentication);
        return ResponseEntity.ok(invites);
    }

    @GetMapping("/inviteManage/recipientEmail")
    public ResponseEntity<List<Invite>> findAllRecipientEmailInvites(@RequestParam String recipientEmail) {
        List<Invite> invites = inviteService.findEmailInvites(recipientEmail);
        return ResponseEntity.ok(invites);
    }
}
