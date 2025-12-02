package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.*;
import unicam.ids.HackHub.enums.SubmissionState;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SUBMISSIONS")
public class Submission {

    public Submission (String title, String content, Date sendingDate) {
        this.title = title;
        this.content = content;
        this.sendingDate = sendingDate;
        this.state = SubmissionState.INVIATA;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SENDING_DATE", nullable = false)
    private Date sendingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_EDIT")
    private Date lastEdit;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", nullable = false)
    private SubmissionState state;

    @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;
}

