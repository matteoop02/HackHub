package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.*;
import unicam.ids.HackHub.enums.PaymentState;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PAYMENTS")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentState status;

    @ManyToOne
    private Hackathon hackathon;

    @ManyToOne
    private Team receivingTeam;

    @ManyToOne
    private User processedBy;
}
