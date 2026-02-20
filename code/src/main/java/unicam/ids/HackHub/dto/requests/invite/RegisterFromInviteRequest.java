package unicam.ids.HackHub.dto.requests.invite;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public record RegisterFromInviteRequest(
        @NotBlank String token,
        @NotBlank String username,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String password,
        @NotNull Date dateOfBirth
) {}
