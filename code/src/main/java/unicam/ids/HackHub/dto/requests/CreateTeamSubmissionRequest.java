package unicam.ids.HackHub.dto.requests;

import org.springframework.security.core.Authentication;

public record CreateTeamSubmissionRequest(
        String title,
        String content
) {}
