package unicam.ids.HackHub.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.UserRoleRepository;

@Configuration
public class RoleInitializer {

    @Bean
    CommandLineRunner initRoles(UserRoleRepository roleRepo) {
        return args -> {
            createRoleIfNotFound(roleRepo, "UTENTE", "Utente base");
            createRoleIfNotFound(roleRepo, "MEMBRO_DEL_TEAM", "Membro di un team");
            createRoleIfNotFound(roleRepo, "LEADER_DEL_TEAM", "Leader di un team");
            createRoleIfNotFound(roleRepo, "MENTORE", "Mentore di hackathon");
            createRoleIfNotFound(roleRepo, "GIUDICE", "Giudice valutatore");
            createRoleIfNotFound(roleRepo, "ORGANIZZATORE", "Organizzatore hackathon");
        };
    }

    private void createRoleIfNotFound(UserRoleRepository repo, String name, String category) {
        if (repo.findByName(name).isEmpty()) {
            UserRole role = new UserRole();
            role.setName(name);       // es. "UTENTE"
            role.setCategory(category); // **obbligatorio!**
            role.setIsActive(true);
            repo.save(role);
        }
    }
}
