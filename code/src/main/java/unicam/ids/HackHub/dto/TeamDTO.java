package unicam.ids.HackHub.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDTO {
    private Long id;
    private String name;
    private Long submissionId;
    private Long hackathonId;
    private Long teamLeaderId;
    private List<Long> membersIds;
    private List<Long> mentorsIds;
}
