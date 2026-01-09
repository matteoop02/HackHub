package unicam.ids.HackHub.repository;

import unicam.ids.HackHub.enums.InviteState;
import unicam.ids.HackHub.model.InviteInsidePlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InsideInviteRepository extends JpaRepository<InviteInsidePlatform, Long> {

    List<InviteInsidePlatform> findByRecipientUsernameAndStatus(String username, InviteState status);

    List<InviteInsidePlatform> findByTeamNameAndStatus(String teamName, InviteState status);

    List<InviteInsidePlatform> findBySenderUsernameAndTeamName(String senderUsername, String teamName);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END " +
            "FROM InviteInsidePlatform i " +
            "WHERE i.recipientUsername = :username AND i.teamName = :teamName AND i.status = :status")
    boolean existsByRecipientUsernameAndTeamNameAndStatus(
            @Param("username") String username,
            @Param("teamName") String teamName,
            @Param("status") InviteState status
    );

    @Query("SELECT i FROM InviteInsidePlatform i WHERE i.status = :status AND i.expiresAt < :dateTime")
            List<InviteInsidePlatform> findExpiredInvites(
            @Param("status") InviteState status,
            @Param("dateTime") LocalDateTime dateTime
    );
}