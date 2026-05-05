package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendSubmissionRequest(
        @Schema(example = "1")
        @NotNull(message = "L'id del team e' obbligatorio")
        Long teamId,

        @Schema(example = "Titolo del progetto sottomesso")
        @NotBlank(message = "Il titolo e' obbligatorio")
        String title,

        @Schema(example = "Questo è il contenuto del progetto presentato.")
        @NotBlank(message = "Il contenuto e' obbligatorio")
        String content
) {
}
