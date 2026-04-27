package unicam.ids.HackHub.dto.responses;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PrizePaymentStatusResponse(
        Long hackathonId,
        String hackathonName,
        Long teamId,
        String teamName,
        Double amount,
        String status,
        LocalDateTime paidAt,
        String transactionReference
) {
}
