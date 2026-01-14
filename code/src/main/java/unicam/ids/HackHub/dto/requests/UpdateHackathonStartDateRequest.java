package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import unicam.ids.HackHub.validation.ChronologicalDates;

import java.time.LocalDateTime;

@ChronologicalDates
public record UpdateHackathonStartDateRequest(
        Authentication authentication,

        @NotEmpty(message = "Il nome dell'hackathon di riferimento non può essere null")
        String hackathonName,

        @NotNull(message = "La nuovo data di inizio deve essere specificata")
        LocalDateTime startDate,

        LocalDateTime endDate
) {}
