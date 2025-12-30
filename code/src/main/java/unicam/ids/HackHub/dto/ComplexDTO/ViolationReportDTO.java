package unicam.ids.HackHub.dto.ComplexDTO;

import lombok.Data;

@Data
public class ViolationReportDTO {
    private Long mentorId;
    private Long teamId;
    private String description;
    private Long organizerId;
}
