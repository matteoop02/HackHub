package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateMentorAvailabilitySlotRequest(
        @Schema(example = "1")
        @NotNull(message = "L'hackathon e' obbligatorio")
        Long hackathonId,

        @Schema(example = "2026-05-10T15:00:00")
        @NotNull(message = "L'inizio dello slot e' obbligatorio")
        @Future(message = "L'inizio dello slot deve essere futuro")
        LocalDateTime startTime,

        @Schema(example = "2026-05-10T15:30:00")
        @NotNull(message = "La fine dello slot e' obbligatoria")
        @Future(message = "La fine dello slot deve essere futura")
        LocalDateTime endTime,

        @Schema(example = "Disponibile per supporto su backend e architettura.")
        @Size(max = 500, message = "Le note non possono superare 500 caratteri")
        String notes
) {
}
