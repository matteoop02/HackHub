package unicam.ids.HackHub.dto.ComplexDTO;

import lombok.Data;

@Data
public class TeamSubscriptionDTO {
    private Long userId;
    private Long hackathonId;
    private Long teamId;
}
