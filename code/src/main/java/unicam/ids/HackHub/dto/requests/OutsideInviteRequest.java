package unicam.ids.HackHub.dto.requests;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class OutsideInviteRequest {
    private String senderUsername;
    @NotBlank(message = "L'email del destinatario è obbligatoria")
    @Email(message = "Formato email non valido")
    private String recipientEmail;

    private String message;
}