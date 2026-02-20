package unicam.ids.HackHub.dto.requests.submission;

import jakarta.validation.constraints.NotEmpty;

public record SubmissionsListByHackathonRequest(

        @NotEmpty(message = "Il nome dell'hackathon non può essere vuoto")
        String hackathonName
) {}
