package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicam.ids.HackHub.enums.CallStatus;
import unicam.ids.HackHub.model.CallBooking;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallBookingRepository extends JpaRepository<CallBooking, Long> {

    @Query("SELECT c FROM CallBooking c WHERE c.mentor = :mentor AND c.status = :status")
    List<CallBooking> findByMentorAndStatus(@Param("mentor") User mentor,@Param("status")  CallStatus status);

    @Query("SELECT c FROM CallBooking c WHERE c.team = :team AND c.status = :status")
    List<CallBooking> findByTeamAndStatus(@Param("team") Team team, @Param("status") CallStatus status);

    @Query("SELECT c FROM CallBooking c WHERE c.team = :team AND c.mentor = :mentor")
    Optional<CallBooking> findByTeamAndMentor(@Param("team") Team team, @Param("mentor") User mentor);
}