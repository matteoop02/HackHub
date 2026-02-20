package unicam.ids.HackHub.dto.requests.submission;

import jakarta.validation.constraints.NotEmpty;

public record SubmissionsListByTeamRequest(

        @NotEmpty(message = "Il nome del team non può essere vuoto")
        String teamName
) {}
