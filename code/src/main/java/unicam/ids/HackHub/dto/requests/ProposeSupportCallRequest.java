package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ProposeSupportCallRequest(
        @Schema(example = "1")
        @NotNull(message = "Il team e' obbligatorio")
        Long teamId,

        @Schema(example = "1")
        Long slotId,

        @Schema(example = "2026-05-10T17:00:00")
        @Future(message = "L'inizio proposto deve essere futuro")
        LocalDateTime proposedStartTime,

        @Schema(example = "2026-05-10T17:30:00")
        @Future(message = "La fine proposta deve essere futura")
        LocalDateTime proposedEndTime,

        @Schema(example = "Supporto sull'architettura del progetto")
        @NotBlank(message = "L'oggetto della call e' obbligatorio")
        @Size(max = 255, message = "L'oggetto non puo' superare 255 caratteri")
        String subject,

        @Schema(example = "Possiamo fare una call per rivedere backend e piano di consegna?")
        @NotBlank(message = "Il messaggio della proposta e' obbligatorio")
        @Size(max = 2000, message = "Il messaggio non puo' superare 2000 caratteri")
        String message
) {
}
