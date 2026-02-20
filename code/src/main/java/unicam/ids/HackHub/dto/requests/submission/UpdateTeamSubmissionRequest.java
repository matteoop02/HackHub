package unicam.ids.HackHub.dto.requests.submission;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.Authentication;

public record UpdateTeamSubmissionRequest(
        Authentication authentication,

        @NotEmpty(message = "Il titolo da modificare non può essere vuoto")
        String title,

        @NotEmpty(message = "Il contenuto da modificare non può essere vuoto")
        String content
) {}
