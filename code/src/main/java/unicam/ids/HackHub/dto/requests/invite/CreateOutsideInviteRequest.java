package unicam.ids.HackHub.dto.requests.invite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateOutsideInviteRequest(
        @Schema(example = "nuovo.utente@example.com")
        @NotBlank(message = "L'email del destinatario è obbligatoria")
        @Email(message = "Fornire un'email valida")
        String recipientEmail,

        @Schema(example = "Vorremmo invitarti a registrarti su HackHub e collaborare con noi.")
        String message
) {}
