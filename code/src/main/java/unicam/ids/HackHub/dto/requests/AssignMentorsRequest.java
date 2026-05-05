package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AssignMentorsRequest(
        @Schema(example = "[8, 9]")
        @NotEmpty(message = "mentorIds obbligatorio")
        List<@NotNull(message = "mentorId non valido") Long> mentorIds
) {
}
