package unicam.ids.HackHub.dto.requests.call;

import jakarta.validation.constraints.NotEmpty;

public record TeamCallsRequest(
        @NotEmpty
        String teamName
) {}