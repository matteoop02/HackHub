package unicam.ids.HackHub.dto.requests.hackathon;

import jakarta.validation.constraints.NotEmpty;

public record DeclareWinnerRequest (
    @NotEmpty
    String hackathonName
) {}
