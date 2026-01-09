package unicam.ids.HackHub.repository;

import unicam.ids.HackHub.enums.InviteState;
import unicam.ids.HackHub.model.InviteOutsidePlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OutsideInviteRepository extends JpaRepository<InviteOutsidePlatform, Long> {

    Optional<InviteOutsidePlatform> findByInviteToken(String token);

    List<InviteOutsidePlatform> findBySenderIdAndStatus(Long senderId, InviteState status);

    List<InviteOutsidePlatform> findByRecipientEmail(String email);

    @Query("SELECT i FROM InviteOutsidePlatform i WHERE i.status = :status AND i.expiresAt < :dateTime")
    List<InviteOutsidePlatform> findExpiredInvites(
            @Param("status") InviteState status,
            @Param("dateTime") LocalDateTime dateTime
    );

    boolean existsByRecipientEmailAndStatus(String email, InviteState status);
}