package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(
        @NotEmpty(message = "Username obbligatorio")
        String username,
        @NotEmpty(message = "Password obbligatoria")
        String password
) {
}