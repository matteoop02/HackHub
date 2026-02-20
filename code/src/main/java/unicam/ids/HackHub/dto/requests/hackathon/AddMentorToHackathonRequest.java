package unicam.ids.HackHub.dto.requests.hackathon;

import jakarta.validation.constraints.NotEmpty;

public record AddMentorToHackathonRequest(
        @NotEmpty
        String hackathonName,
        @NotEmpty
        String mentorUsername
) {}
