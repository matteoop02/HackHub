package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    // Membri del team
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MEMBERS",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> members;

    // Mentori assegnati al team
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MENTORS",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> mentors;

    // Sottomissione associata al team (uno a uno)
    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Submission submission;
}