package unicam.ids.HackHub.bootstrap;

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
            createRoleIfNotFound(roleRepo, 1L,"UTENTE");
            createRoleIfNotFound(roleRepo, 2L,"MEMBRO_DEL_TEAM");
            createRoleIfNotFound(roleRepo, 3L,"LEADER_DEL_TEAM");
            createRoleIfNotFound(roleRepo, 4L,"MENTORE");
            createRoleIfNotFound(roleRepo, 5L,"GIUDICE");
            createRoleIfNotFound(roleRepo, 6L,"ORGANIZZATORE");
        };
    }

    private void createRoleIfNotFound(UserRoleRepository repo, Long id, String name) {
        if (repo.findByName(name).isEmpty()) {
            UserRole role = new UserRole();
            role.setId(id);
            role.setName(name);
            role.setIsActive(true);
            repo.save(role);
        }
    }
}
