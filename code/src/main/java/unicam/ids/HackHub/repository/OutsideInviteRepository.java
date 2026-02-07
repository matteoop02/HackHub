package unicam.ids.HackHub.repository;

import unicam.ids.HackHub.enums.InviteStatus;
import unicam.ids.HackHub.model.InviteOutsidePlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicam.ids.HackHub.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OutsideInviteRepository extends JpaRepository<InviteOutsidePlatform, Long> {

    @Query("SELECT i FROM InviteOutsidePlatform i WHERE i.inviteToken = :token")
    Optional<InviteOutsidePlatform> findByInviteToken(@Param("token") String token);
    @Query("SELECT i FROM InviteOutsidePlatform i WHERE i.status = :status AND i.expiresAt < :dateTime")
    List<InviteOutsidePlatform> findExpiredInvites(@Param("status") InviteStatus status, @Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM InviteOutsidePlatform i " + "WHERE i.recipientEmail = :email AND i.status = :status")
    boolean existsByRecipientEmailAndStatus(@Param("email") String email, @Param("status") InviteStatus status);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM InviteOutsidePlatform i " + "WHERE i.senderUser = :senderUser AND i.recipientEmail = :recipientEmail AND i.status = :status")
    boolean existsBySenderUserAndRecipientEmailAndStatus(@Param("senderUser") User senderUser, @Param("recipientEmail") String recipientEmail, @Param("status") InviteStatus status);

    @Query("SELECT i FROM InviteOutsidePlatform i WHERE i.recipientEmail = :email AND i.status = :status")
    List<InviteOutsidePlatform> findByRecipientEmailAndStatus(@Param("email") String email, @Param("inviteStatus") InviteStatus inviteStatus);
}