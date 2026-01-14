package unicam.ids.HackHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicam.ids.HackHub.model.UserRole;
import unicam.ids.HackHub.repository.UserRoleRepository;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole findUserRoleById(Long userRoleId) {
        return userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> new IllegalArgumentException("Ruolo non trovato"));
    }

    public UserRole getDefaultUserRole() {
        return UserRole.builder()
                .id(1L)
                .name("UTENTE")
                .isActive(true)
                .build();
    }
}
