package unicam.ids.HackHub.dto.responses;

import unicam.ids.HackHub.enums.PaymentStatus;
import java.time.LocalDateTime;

public record PaymentStatusResponse(
        boolean paymentExists,
        PaymentStatus status,
        String receivingTeamName,
        String processedByUsername,
        LocalDateTime paymentDate,
        Double amount,
        Double expectedReward,
        boolean amountMatchesReward,
        boolean receivingTeamMatchesWinner
) {}