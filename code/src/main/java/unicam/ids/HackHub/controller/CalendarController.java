package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.requests.BookSupportCallSlotRequest;
import unicam.ids.HackHub.dto.requests.CreateMentorAvailabilitySlotRequest;
import unicam.ids.HackHub.dto.requests.ProposeSupportCallRequest;
import unicam.ids.HackHub.dto.responses.MentorAvailabilitySlotResponse;
import unicam.ids.HackHub.dto.responses.SupportCallProposalResponse;
import unicam.ids.HackHub.service.CalendarService;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
@Tag(name = "Calendar", description = "Calendario disponibilita' e call di supporto dei mentori")
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/slots")
    @Operation(summary = "Definisci slot di disponibilita'", description = "Permette a un mentore di dichiarare gli slot disponibili per call di supporto.")
    @ApiResponse(responseCode = "201", description = "Slot creato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<MentorAvailabilitySlotResponse> createAvailabilitySlot(
            Authentication authentication,
            @RequestBody @Valid CreateMentorAvailabilitySlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.createAvailabilitySlot(authentication, request));
    }

    @PostMapping("/support-calls")
    @Operation(summary = "Proponi una call di supporto", description = "Permette a un mentore di proporre una call di supporto a un team.")
    @ApiResponse(responseCode = "201", description = "Proposta creata con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<SupportCallProposalResponse> proposeSupportCall(
            Authentication authentication,
            @RequestBody @Valid ProposeSupportCallRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.proposeSupportCall(authentication, request));
    }

    @PostMapping("/slots/{slotId}/book")
    @Operation(
            summary = "Prenota slot call con mentore",
            description = "Permette al leader del team di prenotare uno slot per una call con il mentore."
    )
    @ApiResponse(responseCode = "201", description = "Slot prenotato con successo")
    @ApiResponse(responseCode = "400", description = "Errore nella prenotazione dello slot")
    public ResponseEntity<SupportCallProposalResponse> bookSupportCallSlot(Authentication authentication,
            @PathVariable Long slotId,
            @RequestBody @Valid BookSupportCallSlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarService.bookSupportCallSlot(authentication, slotId, request));
    }

    @GetMapping("/support-calls/me")
    @Operation(summary = "Visualizza richieste di supporto del mentore", description = "Permette al mentore di visualizzare le richieste e le prenotazioni di supporto a lui associate.")
    @ApiResponse(responseCode = "200", description = "Richieste di supporto ottenute con successo")
    public ResponseEntity<List<SupportCallProposalResponse>> getMentorSupportRequests(Authentication authentication) {
        return ResponseEntity.ok(calendarService.getMentorSupportRequests(authentication));
    }

    @DeleteMapping("/support-calls/{supportCallId}")
    @Operation(summary = "Cancella prenotazione call da parte del mentore", description = "Permette al mentore di annullare una prenotazione o richiesta di call di supporto e liberare lo slot associato.")
    @ApiResponse(responseCode = "200", description = "Prenotazione annullata con successo")
    public ResponseEntity<String> cancelSupportCallBooking(Authentication authentication, @PathVariable Long supportCallId) {
        calendarService.cancelSupportCallBooking(authentication, supportCallId);
        return ResponseEntity.ok("Prenotazione annullata con successo");
    }
}
