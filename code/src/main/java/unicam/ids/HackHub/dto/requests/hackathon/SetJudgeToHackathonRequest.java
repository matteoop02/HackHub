package unicam.ids.HackHub.dto.requests.hackathon;

import jakarta.validation.constraints.NotEmpty;

public record SetJudgeToHackathonRequest(
        @NotEmpty
        String hackathonName,
        @NotEmpty
        String judgeUsername
) {}
