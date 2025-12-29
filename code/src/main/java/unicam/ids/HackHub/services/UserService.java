package unicam.ids.HackHub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.ids.HackHub.dto.UserDTO;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.repository.UserRoleRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public User login(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
        return userOptional.orElse(null);
    }

    public boolean usernameAlreadyExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public void register(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username già in uso");
        }
        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email già in uso");
        }
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        UserRole role = userRoleRepository.findByName("Utente");
        user.setRole(role);
        userRepository.save(user);
    }

    public boolean isStaff(User user) {
        if (user.getRole().getCategory().toString().equalsIgnoreCase("membro dello staff")) {
            return true;
        } else {
            return false;
        }
    }
}
