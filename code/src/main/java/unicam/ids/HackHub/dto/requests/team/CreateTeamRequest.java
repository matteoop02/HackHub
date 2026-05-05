package unicam.ids.HackHub.dto.requests.team;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateTeamRequest(
        @Schema(example = "Code Titans")
        @NotEmpty(message = "Il nome del team non può essere nullo")
        String name,

        @Schema(example = "true")
        @NotNull(message = "Specificare se il team sarà Pubblico (true) o Privato (false)")
        boolean isPublic
) {}
