package unicam.ids.HackHub.dto.requests.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.util.Date;

public record RegisterUserRequest<Int>(
        @NotEmpty(message = "Username obbligatorio")
        String username,

        @NotEmpty(message = "Email obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotEmpty(message = "Password obbligatoria")
        String password,

        @JsonProperty("name")
        @NotEmpty(message = "Nome obbligatorio")
        String name,

        @JsonProperty("surname")
        @NotEmpty(message = "Cognome obbligatorio")
        String surname,

        @NotNull(message = "Data di nascita obbligatoria")
        Date dateOfBirth,

        @NotNull(message = "Id del ruolo")
        Long roleId

) {}