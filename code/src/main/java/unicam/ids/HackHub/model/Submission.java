package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import unicam.ids.HackHub.enums.SubmissionState;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SUBMISSIONS")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Content", columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SendingDate", nullable = false)
    private Date sendingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastEdit")
    private Date lastEdit;

    @Enumerated(EnumType.STRING)
    @Column(name = "State", nullable = false)
    private SubmissionState state;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @Column(name = "Score")
    private Double score;

    @Column(name = "Comment", length = 1000)
    private String comment;

    @OneToOne
    @JoinColumn(name = "TeamID")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "HackathonID")
    private Hackathon hackathon;
}