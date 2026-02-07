package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unicam.ids.HackHub.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
