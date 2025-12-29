package unicam.ids.HackHub.dto;

import lombok.Data;
import unicam.ids.HackHub.enums.SubmissionState;

import java.util.Date;

@Data
public class SubmissionDTO {
    private Long id;
    private String title;
    private String content;
    private Date sendingDate;
    private Date lastEdit;
}
