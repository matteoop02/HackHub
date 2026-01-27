package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;

public record DeclareWinnerRequest (
    @NotEmpty
    String hackathonName
) {}
