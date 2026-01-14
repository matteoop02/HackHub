package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;

public record SignTeamRequest(
        @NotEmpty(message = "Il nome dell'hackathon di riferimento non può essere nullo")
        String hackathonName
) {}
