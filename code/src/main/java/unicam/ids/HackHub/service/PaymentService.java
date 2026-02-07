package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.enums.PaymentStatus;
import unicam.ids.HackHub.model.*;
import unicam.ids.HackHub.repository.PaymentRepository;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void payWinner(Hackathon hackathon, User organizer) {

        if (hackathon.getReward() == null || hackathon.getReward() <= 0)
            throw new IllegalStateException("Non c'è nessun premio previsto per questo hackathon");

        Team winner = hackathon.getTeamWinner();
        if (winner == null)
            throw new IllegalStateException("Non è stato assegnato alcun vincitore");


        Payment payment = Payment.builder()
                .amount(hackathon.getReward())
                .paymentDate(LocalDateTime.now())
                .hackathon(hackathon)
                .receivingTeam(winner)
                .processedBy(organizer)
                .status(PaymentStatus.COMPLETED)
                .build();

        save(payment);
    }

    @Transactional(readOnly = true)
public PaymentStatusResponse verifyHackathonPricePayment(String hackathonName) {
    Hackathon hackathon = hackathonService.findHackathonByName(hackathonName);
    Optional<Payment> paymentOpt =
            paymentRepository.findTopByHackathonIdOrderByPaymentDateDesc(hackathon.getId());

    if (paymentOpt.isEmpty()) {
        return new PaymentStatusResponse(
                false, null, null, null, null, null,
                hackathon.getReward(),
                false, false
        );
    }

    Payment payment = paymentOpt.get();
    Team winner = hackathon.getTeamWinner();
    boolean receivingMatchesWinner =
            winner != null && payment.getReceivingTeam() != null
                    && payment.getReceivingTeam().getId().equals(winner.getId());

    Double expected = hackathon.getReward();
    boolean amountMatchesReward =
            payment.getAmount() != null && expected != null
                    && payment.getAmount().equals(expected);

    return new PaymentStatusResponse(
            true,
            //check dello stato
            payment.getStatus(),
            //check del nome del team che riceve il pagamento
            payment.getReceivingTeam() != null ? payment.getReceivingTeam().getName() : null,
            //check del nome che ha processato il pagamento
            payment.getProcessedBy() != null ? payment.getProcessedBy().getUsername() : null,
            //controllo della data del pagamento
            payment.getPaymentDate(),
            //controllo dell'importo del pagamento
            payment.getAmount(),
            expected,
            amountMatchesReward,
            receivingMatchesWinner
    );
}

    public Payment save(Payment payment) { return paymentRepository.save(payment); }
}
