package unicam.ids.HackHub.dto.responses;

import unicam.ids.HackHub.enums.PaymentState;
import java.time.LocalDateTime;

public record PaymentStatusResponse(
        boolean paymentExists,
        PaymentState status,
        String receivingTeamName,
        String processedByUsername,
        LocalDateTime paymentDate,
        Double amount,
        Double expectedReward,
        boolean amountMatchesReward,
        boolean receivingTeamMatchesWinner
) {}