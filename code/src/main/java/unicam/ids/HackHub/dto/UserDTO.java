package unicam.ids.HackHub.dto;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private Date dateOfBirth;
    private Long roleId;
}
