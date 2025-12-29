package unicam.ids.HackHub.dto.ComplexDTO;

import lombok.Data;
import unicam.ids.HackHub.dto.TeamDTO;
import unicam.ids.HackHub.dto.UserDTO;

@Data
public class UserTeamDTO {
    private UserDTO userDTO;
    private TeamDTO teamDTO;
}
