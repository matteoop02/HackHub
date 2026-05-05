package unicam.ids.HackHub.dto.requests.submission;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

public record UpdateTeamSubmissionRequest(
        @Schema(example = "1")
        @NotNull(message = "L'ID della sottomissione è obbligatorio")
        Long submissionId,

        @Schema(example = "Nuovo Titolo Progetto")
        @NotEmpty(message = "Il titolo da modificare non può essere vuoto")
        String title,

        @Schema(example = "Questo è il contenuto del progetto modificato con le correzioni richieste.")
        @NotEmpty(message = "Il contenuto da modificare non può essere vuoto")
        String content
) {}
