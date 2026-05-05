package unicam.ids.HackHub.dto.requests.invite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record RejectInsideInviteRequest(
    @Schema(example = "1")
    @NotNull
    Long inviteId
) {}
