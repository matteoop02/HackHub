package unicam.ids.HackHub.repository;

import unicam.ids.HackHub.enums.InviteStatus;
import unicam.ids.HackHub.model.InviteInsidePlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InsideInviteRepository extends JpaRepository<InviteInsidePlatform, Long> {

    @Query("SELECT i FROM InviteInsidePlatform i WHERE i.recipientUser = :recipientUser AND i.status = :status")
    List<InviteInsidePlatform> findByRecipientUserAndStatus(@Param("recipientUser") User recipientUser, @Param("status") InviteStatus status);

    @Query("SELECT i FROM InviteInsidePlatform i WHERE i.team = :team AND i.status = :status")
    List<InviteInsidePlatform> findByTeamAndStatus(@Param("team") Team team, @Param("status") InviteStatus status);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM InviteInsidePlatform i " + "WHERE i.recipientUser = :recipientUser AND i.team = :team AND i.status = :status")
    boolean existsByRecipientUserAndTeamAndStatus(@Param("recipientUser") User recipientUser, @Param("team") Team team, @Param("status") InviteStatus status);

    @Query("SELECT i FROM InviteInsidePlatform i WHERE i.status = :status AND i.expiresAt < :dateTime")
    List<InviteInsidePlatform> findExpiredInvites(@Param("status") InviteStatus status, @Param("dateTime") LocalDateTime dateTime);
}
