package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.ids.HackHub.exceptions.ResourceNotFoundException;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.TeamRepository;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.repository.UserRoleRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private EmailService emailService;

    public void violationReportRequest(Long mentorId, String description, Long teamId, Long organizerId) {
        Optional<User> mentor = userRepository.findById(mentorId);
        if(mentor.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        Optional<User> organizer = userRepository.findById(organizerId);
        if(organizer.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }
//        if(!isType(organizer.get(), "organizzatore")) {
//            throw new IllegalArgumentException("Organizzatore non trovato");
//        }
        Optional<Team> team = teamRepository.findById(teamId);
        if(team.isEmpty()) {
            throw new IllegalArgumentException("Team non trovato");
        }
        if(team.get().getHackathon() == null) {
            throw new IllegalArgumentException("Hackathon non trovato");
        }
        if(team.get().getHackathon().getRegulation().isEmpty()) {
            throw new IllegalArgumentException("Regolamento hackathon non trovato");
        }
        emailService.sendEmail(organizer.get().getEmail(), "Segnalazione Team - Hackathon: " + team.get().getHackathon().getName() + ". - Team: " + team.get().getName() + ". - Mentore: " + mentor.get().getEmail(), description);
    }

    public UserRole getDefaultUserRole() {
        return userRoleRepository.findByNameAndIsActiveTrue("UTENTE")
                .orElseThrow(() -> new ResourceNotFoundException("Ruolo non trovato: UTENTE>"));
    }

    public UserRole getUserRoleById(Long userRoleId) {
        return userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new IllegalArgumentException("Ruolo non trovato"));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
