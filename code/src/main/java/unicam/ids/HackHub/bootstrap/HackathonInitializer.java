package unicam.ids.HackHub.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import unicam.ids.HackHub.enums.HackathonStatus;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.HackathonRepository;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.repository.UserRoleRepository;
import unicam.ids.HackHub.service.UserService;

import java.time.LocalDateTime;

@Configuration
public class HackathonInitializer {

    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;

    public HackathonInitializer(UserRepository userRepository, HackathonRepository hackathonRepository) {
        this.userRepository = userRepository;
        this.hackathonRepository = hackathonRepository;
    }

    @Bean
    @Order(3)
    CommandLineRunner initHackathon(HackathonRepository repo) {
        return args -> {
            createHackathonIfNotFound(repo);
        };
    }

    private void createHackathonIfNotFound(HackathonRepository repo) {
        if (repo.findHackathonByName("AAA").isEmpty()) {

            User user = userRepository.findByUsername("pisto").orElse(null);

            Hackathon hackathon = Hackathon.builder()
                    .name("AAA".trim())
                    .place("AAA")
                    .regulation("AAA")
                    .subscriptionDeadline(LocalDateTime.now())
                    .startDate(LocalDateTime.MAX)
                    .endDate(LocalDateTime.MAX)
                    .reward(2000.0)
                    .maxTeamSize(5)
                    .isPublic(true)
                    .state(HackathonStatus.IN_ISCRIZIONE)
                    .organizer(user)
                    .build();
            repo.save(hackathon);
        }
    }
}
