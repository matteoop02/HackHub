package unicam.ids.HackHub.factory;

import unicam.ids.HackHub.enums.InviteState;
import unicam.ids.HackHub.model.InviteInsidePlatform;
import unicam.ids.HackHub.model.InviteOutsidePlatform;
import org.springframework.stereotype.Component;
import unicam.ids.HackHub.model.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class InviteFactory {

    private static final int OUTSIDE_INVITE_EXPIRY_DAYS = 7;
    private static final int TEAM_INVITE_EXPIRY_DAYS = 30;

    public InviteOutsidePlatform createOutsideInvite(
            String senderName,
            String recipientEmail,
            String message) {

        return InviteOutsidePlatform.builder()
                .senderName(senderName)
                .recipientEmail(recipientEmail)
                .inviteToken(generateToken())
                .status(InviteState.PENDING)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(OUTSIDE_INVITE_EXPIRY_DAYS))
                .message(message)
                .build();
    }

    public InviteInsidePlatform createTeamInvite(
            String senderUsername,
            String recipientUsername,
            String teamName,
            UserRole proposedRole,
            String message) {

        return InviteInsidePlatform.builder()
                .senderUsername(senderUsername)
                .recipientUsername(recipientUsername)
                .teamName(teamName)
                .proposedRole(proposedRole)
                .status(InviteState.PENDING)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(TEAM_INVITE_EXPIRY_DAYS))
                .message(message)
                .build();
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}