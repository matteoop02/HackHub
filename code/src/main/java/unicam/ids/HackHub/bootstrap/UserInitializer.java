package unicam.ids.HackHub.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.UserRepository;
import unicam.ids.HackHub.repository.UserRoleRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class UserInitializer {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    CommandLineRunner initUsers(UserRepository userRepo, UserRoleRepository userRoleRepo) {
        return args -> {
            createUserIfNotFound(userRepo, userRoleRepo,
                    "matteo", "fagnani", "matteop",
                    "matteo.fagnani@studenti.unicam.it",
                    LocalDate.of(2002, 3, 2),
                    "UTENTE"
            );

            createUserIfNotFound(userRepo, userRoleRepo,
                    "andrea", "pistolesi", "pisto",
                    "andrea.pistolesi@studenti.unicam.it",
                    LocalDate.of(2003, 3, 2),
                    "ORGANIZZATORE"
            );

            createUserIfNotFound(userRepo, userRoleRepo,
                    "marco", "solustri", "marc",
                    "marco.solustri@studenti.unicam.it",
                    LocalDate.of(2004, 3, 2),
                    "MENTORE"
            );

            createUserIfNotFound(userRepo, userRoleRepo,
                    "luca", "prada", "luke",
                    "luca.prada@studenti.unicam.it",
                    LocalDate.of(2004, 3, 2),
                    "UTENTE"
            );
        };
    }

    private void createUserIfNotFound(UserRepository userRepo,
                                      UserRoleRepository userRoleRepo,
                                      String name,
                                      String surname,
                                      String username,
                                      String email,
                                      LocalDate dateOfBirth,
                                      String roleName) {
        if (userRepo.findByUsername(username).isEmpty()) {
            UserRole role = userRoleRepo.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode("123456789"))
                    .dateOfBirth(Date.from(dateOfBirth.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    .role(role)
                    .isDeleted(false)
                    .build();

            userRepo.save(user);
        }
    }
}
