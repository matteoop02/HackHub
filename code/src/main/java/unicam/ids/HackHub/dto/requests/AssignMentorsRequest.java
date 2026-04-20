package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AssignMentorsRequest(
        @NotEmpty(message = "mentorIds obbligatorio")
        List<@NotNull(message = "mentorId non valido") Long> mentorIds
) {
}
