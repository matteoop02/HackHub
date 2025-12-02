package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TEAMS")
public class Team {

    public Team(String name, User creator, Hackathon hackathon) {
        this.name = name;
        this.members = new ArrayList<User>();
        this.members.add(creator);
        this.mentors = new ArrayList<User>();
        this.submission = null;
        this.hackathon = hackathon;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<User> members;

    @ManyToMany
    @JoinTable(name = "MENTORI", joinColumns = @JoinColumn(name = "TEAM_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> mentors;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Submission submission;

    @ManyToOne
    @JoinColumn(name = "HACKATHON_ID")
    private Hackathon hackathon;
}