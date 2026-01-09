package unicam.ids.HackHub.dto.requests;

import lombok.Data;

import java.util.Date;

@Data
public class CreateHackathonRequest {
    private String name;
    private String place;
    private String regulation;
    private Date subscriptionDeadline;
    private Date startDate;
    private Date endDate;
    private double reward;
    private int maxTeamSize;

}
