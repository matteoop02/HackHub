package unicam.ids.HackHub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_ROLES")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "Category", nullable = false)
    private String category;

    @Size(max = 255)
    @Column(name = "Name", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "Description", nullable = true)
    private String description;

    @NotNull
    @ColumnDefault("TRUE")
    @Column(name = "IsActive", nullable = false)
    private Boolean isActive = true;
}