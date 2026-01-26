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

    public Payment save(Payment payment) { return paymentRepository.save(payment); }
}
