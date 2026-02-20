package unicam.ids.HackHub.dto.requests.call;

import jakarta.validation.constraints.NotEmpty;

public record CancelCallRequest(
        @NotEmpty(message = "L'username del mentore non può essere vuoto")
        String mentorUsername
) {}