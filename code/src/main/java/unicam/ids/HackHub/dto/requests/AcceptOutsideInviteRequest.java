package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record AcceptOutsideInviteRequest(
        @NotBlank(message = "token obbligatorio")
        String token
) {
}
