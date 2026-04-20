package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotNull;

public record AssignJudgeRequest(
        @NotNull(message = "judgeId obbligatorio")
        Long judgeId
) {
}
