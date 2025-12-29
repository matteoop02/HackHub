package unicam.ids.HackHub.dto.ComplexDTO;

import lombok.Data;
import unicam.ids.HackHub.dto.HackathonDTO;
import unicam.ids.HackHub.dto.UserDTO;

@Data
public class UserHackathonDTO {
    private UserDTO userDTO;
    private HackathonDTO hackathonDTO;
}
