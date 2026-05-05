package unicam.ids.HackHub.dto.requests.invite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record RejectOutsideInviteRequest(
    @Schema(example = "abc123TOKENabc123")
    @NotEmpty
    String token
) {}
