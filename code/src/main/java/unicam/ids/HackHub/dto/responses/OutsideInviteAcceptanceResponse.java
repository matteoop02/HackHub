package unicam.ids.HackHub.dto.responses;

import lombok.Builder;

@Builder
public record OutsideInviteAcceptanceResponse(
        String recipientEmail,
        String message,
        String status,
        String nextStep
) {
}
