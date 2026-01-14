package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import lombok.*;
import unicam.ids.HackHub.enums.ReportType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "REPORTS")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Description", length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "ReportType")
    private ReportType type;

    @ManyToOne
    @JoinColumn(name = "Team_Id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "Hackathon_Id", nullable = false)
    private Hackathon hackathon;

    @ManyToOne
    @JoinColumn(name = "Mentor_Id", nullable = false)
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "Organizer_Id", nullable = false)
    private User organizer;
}
