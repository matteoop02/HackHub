package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Size(max = 255)
    @NotNull
    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Size(max = 500)
    @NotNull
    @Column(name = "PASSWORD_HASH", nullable = false, length = 500)
    private String passwordHash;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = true)
    private Team team;
}