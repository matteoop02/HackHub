package unicam.ids.HackHub.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleService userRoleService;

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsUserByEmail(String email) {
    return userRepository.existsByEmail(email);
}


    public void changeRole(User user, Long roleId){
        user.setRole(userRoleService.findUserRoleById(roleId));
    }

    @Transactional
    public void assignTeamToUser(String username, Team team) {
        User user = this.findUserByUsername(username);
        user.setTeam(team);
    }

    public void save(User user) {
        userRepository.save(user);
    }

}
