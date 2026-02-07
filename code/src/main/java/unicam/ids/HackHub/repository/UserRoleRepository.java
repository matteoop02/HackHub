package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unicam.ids.HackHub.model.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("SELECT r FROM UserRole r WHERE r.name = :name")
    Optional<UserRole> findByName(@Param("name") String name);

    @Query("SELECT r FROM UserRole r WHERE r.name = :name AND r.isActive = true")
    Optional<UserRole> findByNameAndIsActiveTrue(@Param("utente") String utente);
}