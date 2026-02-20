package unicam.ids.HackHub.dto.requests.hackathon;

import jakarta.validation.constraints.NotEmpty;

public record RemoveMentorFromHackathonRequest(
        @NotEmpty
        String hackathonName,
        @NotEmpty
        String mentorUsername
) {}
