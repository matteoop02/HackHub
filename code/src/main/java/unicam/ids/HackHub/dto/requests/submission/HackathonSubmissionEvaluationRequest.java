package unicam.ids.HackHub.dto.requests.submission;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;

public record HackathonSubmissionEvaluationRequest(
        Authentication authentication,

        @NotEmpty(message = "Il nome dell'hackathon non può essere vuoto")
        String hackathonName,

        @NotEmpty(message = "Il nome del team non può essere vuoto")
        String teamName,

        @NotNull
        Double score,

        @NotEmpty(message = "Il commento della valutazione è obbligatorio")
        String comment
) {}


