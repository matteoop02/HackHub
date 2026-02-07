package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.enums.PaymentStatus;
import unicam.ids.HackHub.model.Payment;

import java.util.List;
import java.util.Optional;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

       Optional<Payment> findTopByHackathonIdOrderByPaymentDateDesc(Long hackathonId);

    List<Payment> findByHackathonId(Long hackathonId);

    boolean existsByHackathonId(Long hackathonId);

    boolean existsByHackathonIdAndStatus(Long hackathonId, PaymentStatus status);
}

