package unicam.ids.HackHub.dto.requests.invite;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record CreateOutsideInviteRequest(
        @NotBlank(message = "L'email del destinatario è obbligatoria")
        @Email(message = "Fornire un'email valida")
        String recipientEmail,

        @NotEmpty
        String message
) {}
