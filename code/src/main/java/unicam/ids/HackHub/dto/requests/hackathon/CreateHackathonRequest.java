package unicam.ids.HackHub.dto.requests.hackathon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import unicam.ids.HackHub.validation.ChronologicalDates;

import java.time.LocalDateTime;

@ChronologicalDates
public record CreateHackathonRequest(
        @Schema(example = "Hackathon Innovazione 2026")
        @NotEmpty(message = "Nome obbligatorio")
        String name,

        @Schema(example = "Camerino")
        @NotEmpty(message = "Luogo obbligatorio")
        String place,

        @Schema(example = "Testo del regolamento...")
        @NotEmpty(message = "Regolamento obbligatorio")
        String regulation,

        @Schema(example = "2026-04-01T23:59:59")
        @NotNull(message = "Limite invio sottomissione obbligatorio")
        LocalDateTime subscriptionDeadline,

        @Schema(example = "2026-05-01T23:59:59")
        @NotNull(message = "Data inizio obbligatoria")
        LocalDateTime startDate,

        @Schema(example = "2026-05-03T23:59:59")
        @NotNull(message = "Data fine obbligatoria")
        LocalDateTime endDate,

        @Schema(example = "5000.0")
        @Positive(message = "Importo del premio deve essere positivo")
        double reward,

        @Schema(example = "5")
        @Positive(message = "Massima dimensione dei team deve essere positiva")
        int maxTeamSize,

        @Schema(example = "true")
        boolean isPublic
) {}

