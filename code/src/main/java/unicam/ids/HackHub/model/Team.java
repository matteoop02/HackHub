package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TEAMS")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Submission submission;

    @ManyToOne
    @JoinColumn(name = "HackathonId")
    private Hackathon hackathon;

    @OneToOne
    @JoinColumn(name = "LeaderId", referencedColumnName = "Id")
    private User teamLeader;

    @OneToMany(mappedBy = "team")
    private List<User> members;

    @ManyToMany
    @JoinTable(name = "MENTORI", joinColumns = @JoinColumn(name = "TeamId"), inverseJoinColumns = @JoinColumn(name = "UserId"))
    private List<User> mentors;
}