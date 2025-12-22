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
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Place", nullable = false)
    private String place;

    @Column(name = "Regulation", nullable = false)
    private String regulation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SubscriptionDeadline", nullable = false)
    private Date subscriptionDeadline;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "StartDate", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EndDate", nullable = false)
    private Date endDate;

    @Column(name = "Reward", nullable = false)
    private double reward;

    @OneToMany(mappedBy = "hackathon")
    private List<Team> teams;

    @Column(name = "MaxTeamSize", nullable = false)
    private int maxTeamSize;

    @OneToOne
    @JoinColumn(name = "WinnerId", referencedColumnName = "Id")
    private Team winner;

    @Enumerated(EnumType.STRING)
    @Column(name = "State", nullable = false)
    private HackathonState state;
}