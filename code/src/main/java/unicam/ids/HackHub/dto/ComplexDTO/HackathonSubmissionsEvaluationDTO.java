package unicam.ids.HackHub.dto.ComplexDTO;

import lombok.Data;

import java.util.Map;

@Data
public class HackathonSubmissionsEvaluationDTO {
    private Long judgeId;
    private Long hackathonId;
    private Map<Long, EvaluationMapValue> judgements;

    @Data
    public static class EvaluationMapValue {
        private Long teamId;
        private Double score;
        private String comment;
    }
}


