package unicam.ids.HackHub.dto.requests.team;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ReportViolationRequest(
        @Schema(example = "Il team ha condiviso materiale non consentito con un altro gruppo durante la fase competitiva.")
        @NotBlank(message = "La descrizione della violazione non può essere vuota")
        String description
) {}
