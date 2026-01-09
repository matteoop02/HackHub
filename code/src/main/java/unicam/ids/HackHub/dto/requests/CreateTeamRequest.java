package unicam.ids.HackHub.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTeamRequest {
    private String username;

    @NotNull(message = "Il nome del team non può essere nullo")
    private String name;

    @NotNull(message = "Il nome dell'hackathon di riferimento non può essere nullo")
    private String hackathonName;

    private String submissionName;
}
