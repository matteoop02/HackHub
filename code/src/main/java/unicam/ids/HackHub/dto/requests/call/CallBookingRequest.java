package unicam.ids.HackHub.dto.requests.call;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CallBookingRequest(
        @NotEmpty
        String teamName,
        @NotEmpty
        String mentorUsername,
        @NotNull
        LocalDateTime startTime,
        @NotNull
        LocalDateTime endTime,
        @NotEmpty
        String topic
) {}