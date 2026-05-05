package unicam.ids.HackHub.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookSupportCallSlotRequest(
        @Schema(example = "Supporto sulla demo finale")
        @NotBlank(message = "L'oggetto della call e' obbligatorio")
        @Size(max = 255, message = "L'oggetto non puo' superare 255 caratteri")
        String subject,

        @Schema(example = "Vorremmo usare questo slot per rivedere demo e pitch.")
        @NotBlank(message = "Il messaggio della call e' obbligatorio")
        @Size(max = 2000, message = "Il messaggio non puo' superare 2000 caratteri")
        String message
) {
}
