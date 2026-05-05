package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AssignJudgeRequest(
        @Schema(example = "7")
        @NotNull(message = "judgeId obbligatorio")
        Long judgeId
) {
}
