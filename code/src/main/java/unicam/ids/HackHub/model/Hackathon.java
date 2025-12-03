package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicam.ids.HackHub.enums.HackathonState;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HACKATHONS")
public class Hackathon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PLACE", nullable = false)
    private String place;

    @Column(name = "REGULATION", nullable = false)
    private String regulation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SUBSCRIPTION_DEADLINE", nullable = false)
    private Date subscriptionDeadline;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "REWARD", nullable = false)
    private double reward;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    @Column(name = "MAX_TEAM_SIZE", nullable = false)
    private int maxTeamSize;

    @OneToOne
    @JoinColumn(name = "WINNER_ID", referencedColumnName = "ID")
    private Team winner;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", nullable = false)
    private HackathonState state;
}

