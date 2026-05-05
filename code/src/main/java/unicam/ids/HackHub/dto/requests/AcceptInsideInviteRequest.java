package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AcceptInsideInviteRequest(
        @Schema(example = "1")
        @NotNull(message = "L'id dell'invito e' obbligatorio")
        Long inviteId
) {
}
