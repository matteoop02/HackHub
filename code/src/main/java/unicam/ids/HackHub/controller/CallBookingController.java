package unicam.ids.HackHub.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import unicam.ids.HackHub.dto.requests.CallBookingRequest;
import unicam.ids.HackHub.model.CallBooking;
import unicam.ids.HackHub.service.CallBookingService;

import java.util.List;

@RestController
@RequestMapping("/api/calls")
@Tag(name = "Prenotazioni Call", description = "Permette di prenotare call con i mentori sia del team che esterni")
public class CallBookingController {

    @Autowired
    private CallBookingService callBookingService;

    @GetMapping("/staff/callForMentor")
    public ResponseEntity<List<CallBooking>> getCallForMentor(@RequestParam String mentorUsername){
        List<CallBooking> calls = callBookingService.getBookingsForMentor(mentorUsername);
        return ResponseEntity.ok(calls);
    }

    @GetMapping("/team/callForTeam")
    public ResponseEntity<List<CallBooking>> getCallForTeam(@RequestParam String teamName){
        List<CallBooking> calls = callBookingService.getBookingsForTeam(teamName);
        return ResponseEntity.ok(calls);
    }

    @PostMapping("/leaderDelTeam/book")
    public ResponseEntity<CallBooking> bookCall(Authentication authentication, @RequestBody @Valid CallBookingRequest request) {
        CallBooking booking = callBookingService.bookCall(
                authentication,
                request.mentorUsername(),
                request.startTime(),
                request.endTime(),
                request.topic()
        );
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/leaderDelTeam/cancel")
    public ResponseEntity<CallBooking> cancelCall(Authentication authentication, @RequestParam String mentorUsername) {
        CallBooking booking = callBookingService.cancelCall(
                authentication,
                mentorUsername
        );
        return ResponseEntity.ok(booking);
    }
}
