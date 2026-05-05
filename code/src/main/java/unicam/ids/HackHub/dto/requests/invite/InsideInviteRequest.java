package unicam.ids.HackHub.dto.requests.invite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record InsideInviteRequest (
        @Schema(example = "2")
        @NotNull(message = "L'id del destinatario è obbligatorio")
        Long recipientId,

        @Schema(example = "1")
        @NotNull(message = "L'id del ruolo proposto è obbligatorio")
        Long proposedRoleId,

        @Schema(example = "Ti invitiamo a unirti al nostro team per l'hackathon.")
        String message
) {}