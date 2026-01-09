package unicam.ids.HackHub.controller;

import unicam.ids.HackHub.dto.requests.InsideInviteRequest;
import unicam.ids.HackHub.dto.requests.OutsideInviteRequest;
import unicam.ids.HackHub.model.*;
import unicam.ids.HackHub.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invites")
@RequiredArgsConstructor
public class InviteController {

    private final InviteService inviteService;

    @PostMapping("/outside")
    public ResponseEntity<InviteOutsidePlatform> inviteOutsideUser(
            @Valid @RequestBody OutsideInviteRequest request) {

        InviteOutsidePlatform invite = inviteService.inviteOutsideUser(
                request.getSenderUsername(),
                request.getRecipientEmail(),
                request.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(invite);
    }

    @PostMapping("/team")
    public ResponseEntity<InviteInsidePlatform> inviteToTeam(
            @Valid @RequestBody InsideInviteRequest request) {

        InviteInsidePlatform invite = inviteService.inviteUserToTeam(
                request.getSenderUsername(),
                request.getRecipientUsername(),
                request.getTeamName(),
                request.getProposedRole(),
                request.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(invite);
    }

    @PostMapping("/outside/{token}/accept")
    public ResponseEntity<Void> acceptOutsideInvite(@PathVariable String token) {
        inviteService.acceptOutsideInvite(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/team/{inviteId}/accept")
    public ResponseEntity<Void> acceptTeamInvite(
            @PathVariable Long inviteId,
            @AuthenticationPrincipal String username) {

        inviteService.acceptTeamInvite(inviteId, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/team/{inviteId}/reject")
    public ResponseEntity<Void> rejectInvite(
            @PathVariable Long inviteId,
            @AuthenticationPrincipal String username) {

        inviteService.rejectInvite(inviteId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/team/{inviteId}")
    public ResponseEntity<Void> cancelInvite(
            @PathVariable Long inviteId,
            @AuthenticationPrincipal String username) {

        inviteService.cancelInvite(inviteId, username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<InviteInsidePlatform>> getPendingInvites(
            @AuthenticationPrincipal String username) {

        List<InviteInsidePlatform> invites =
                inviteService.getPendingInvitesForUser(username);
        return ResponseEntity.ok(invites);
    }

    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<InviteInsidePlatform>> getTeamInvites(
            @PathVariable String teamName) {

        List<InviteInsidePlatform> invites = inviteService.getTeamInvites(teamName);
        return ResponseEntity.ok(invites);
    }
}
