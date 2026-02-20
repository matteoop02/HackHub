package unicam.ids.HackHub.dto.requests.team;

import jakarta.validation.constraints.NotEmpty;

public record RemoveMemberToTeamRequest (

        @NotEmpty(message = "il membro non può essere vuoto")
        String member
) {}
