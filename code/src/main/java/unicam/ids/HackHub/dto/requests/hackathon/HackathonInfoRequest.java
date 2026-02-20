package unicam.ids.HackHub.dto.requests.hackathon;

import jakarta.validation.constraints.NotEmpty;

public record HackathonInfoRequest (
        @NotEmpty
        int id,
        @NotEmpty
        String name
) {}
