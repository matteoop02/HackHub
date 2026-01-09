package unicam.ids.HackHub.dto.requests;

import unicam.ids.HackHub.model.UserRole;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class InsideInviteRequest {
    @NotBlank(message = "Lo username mittente è obbligatorio")
    private String senderUsername;

    @NotBlank(message = "Lo username destinatario è obbligatorio")
    private String recipientUsername;

    @NotBlank(message = "Il nome del team è obbligatorio")
    private String teamName;

    @NotNull(message = "Il ruolo proposto è obbligatorio")
    private UserRole proposedRole;

    private String message;
}