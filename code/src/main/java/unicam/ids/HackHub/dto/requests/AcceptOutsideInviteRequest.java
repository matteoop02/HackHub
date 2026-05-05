package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AcceptOutsideInviteRequest(
        @Schema(example = "abc123TOKENabc123")
        @NotBlank(message = "token obbligatorio")
        String token
) {
}
