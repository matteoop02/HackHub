package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record RegisterFromInviteRequest(
        @NotBlank String token,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String password
) {}
