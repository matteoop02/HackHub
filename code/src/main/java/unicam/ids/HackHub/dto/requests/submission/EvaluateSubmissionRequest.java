package unicam.ids.HackHub.dto.requests.submission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EvaluateSubmissionRequest(
        @Schema(example = "1")
        @NotNull(message = "L'ID della sottomissione è obbligatorio")
        Long submissionId,

        @Schema(example = "9.0")
        @NotNull(message = "Il punteggio è obbligatorio")
        @DecimalMin(value = "0.0", message = "Il punteggio minimo è 0.0")
        @DecimalMax(value = "10.0", message = "Il punteggio massimo è 10.0")
        Double score,

        @Schema(example = "Valutazione aggiornata dopo revisione finale del progetto.")
        String comment
) {}
