package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record DeclareWinningTeamRequest(
        @Schema(example = "1")
        @NotNull(message = "Il team vincitore e' obbligatorio")
        Long teamId
) {
}
