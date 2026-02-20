package unicam.ids.HackHub.dto.requests.team;

import jakarta.validation.constraints.NotEmpty;

public record LeaveTeamRequest (

        @NotEmpty(message = "il nome del team non può essere vuoto")
        String teamName
) {}
