package unicam.ids.HackHub.dto.requests.invite;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;

public record OutsideInviteRequest (

        @NotEmpty(message = "L'email del destinatario è obbligatoria")
        @Email(message = "Formato email non valido")
        String recipientEmail,

        String message
) {}

