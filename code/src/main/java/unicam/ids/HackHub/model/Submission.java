package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import unicam.ids.HackHub.enums.SubmissionState;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUBMISSIONS")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TITOLO", nullable = false)
    private String titolo;

    @Column(name = "CONTENUTO", columnDefinition = "TEXT")
    private String contenuto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INVIO", nullable = false)
    private Date dataInvio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ULTIMA_MODIFICA")
    private Date ultimaModifica;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATO", nullable = false)
    private SubmissionState stato;

    // Relazione OneToOne con Team
    @OneToOne
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;
}

