package unicam.ids.HackHub.dto;

import lombok.*;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Team;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HackathonDTO {
    private Long id;
    private String name;
    private String place;
    private String regulation;
    private Date subscriptionDeadline;
    private Date startDate;
    private Date endDate;
    private double reward;
    private List<Team> teams;
    private int maxTeamSize;
    private Long winnerId;
    private HackathonState hackathonState;
}
