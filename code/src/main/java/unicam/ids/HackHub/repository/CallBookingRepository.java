package unicam.ids.HackHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicam.ids.HackHub.enums.CallStatus;
import unicam.ids.HackHub.model.CallBooking;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallBookingRepository extends JpaRepository<CallBooking, Long> {
    List<CallBooking> findByMentorAndStatus(User mentor, CallStatus status);
    List<CallBooking> findByTeamAndStatus(Team team, CallStatus status);
    Optional<CallBooking> findByTeamAndMentor(Team team, User mentor);
}