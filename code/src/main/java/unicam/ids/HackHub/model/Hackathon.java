package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unicam.ids.HackHub.enums.HackathonState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Hackathons")
public class Hackathon {

    public Hackathon(String name, String place, String regulation, Date subscriptionDeadline, Date startDate, Date endDate, double prize, int maxTeamSize) {
        this.name = name;
        this.place = place;
        this.regulation = regulation;
        this.subscriptionDeadline = subscriptionDeadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prize = prize;
        this.maxTeamSize = maxTeamSize;
        this.teams = new ArrayList<Team>();
        this.winner = null;
        this.state = HackathonState.IN_ISCRIZIONE;
    }

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

    @Column(name = "PRIZE", nullable = false)
    private double prize;

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    @Column(name = "MAX_TEAM_SIZE", nullable = false)
    private int maxTeamSize;

    @OneToOne
    @JoinColumn(name = "WINNER_ID")
    private Team winner;

    @Column(name = "STATE", nullable = false)
    private HackathonState state;
}
