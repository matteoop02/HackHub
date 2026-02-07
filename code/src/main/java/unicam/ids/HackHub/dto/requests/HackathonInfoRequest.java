package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;

public record HackathonInfoRequest (
        @NotEmpty
        int id,
        @NotEmpty
        String name
) {}
