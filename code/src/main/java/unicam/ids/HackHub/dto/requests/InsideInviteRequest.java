package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record InsideInviteRequest (
        @NotEmpty(message = "Lo username mittente è obbligatorio")
        String senderUsername,

        @NotEmpty(message = "Lo username destinatario è obbligatorio")
        String recipientUsername,

        @NotNull(message = "L'id del ruolo proposto è obbligatorio")
        Long proposedRoleId,

        String message
) {}