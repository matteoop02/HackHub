package unicam.ids.HackHub.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import unicam.ids.HackHub.enums.CallStatus;
import unicam.ids.HackHub.model.CallBooking;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.repository.CallBookingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CallBookingService {

    @Autowired
    private CallBookingRepository callBookingRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @Transactional
    public CallBooking bookCall(Authentication authentication, String mentorUsername, LocalDateTime start, LocalDateTime end, String topic) {
        User leader = userService.findUserByUsername(authentication.getName());
        Team team = teamService.findByName(leader.getTeam().getName());

        // Solo il leader può prenotare
        if (!team.getTeamLeader().equals(leader)) {
            throw new IllegalArgumentException("Solo il leader del team può prenotare una call.");
        }

        User mentor = userService.findUserByUsername(mentorUsername);

        CallBooking booking = CallBooking.builder()
                .team(team)
                .mentor(mentor)
                .startTime(start)
                .endTime(end)
                .topic(topic)
                .status(CallStatus.PENDING)
                .build();

        return save(booking);
    }

    public List<CallBooking> getBookingsForMentor(String mentorUsername) {
        return callBookingRepository.findByMentorAndStatus(userService.findUserByUsername(mentorUsername), CallStatus.PENDING);
    }

    public List<CallBooking> getBookingsForTeam(String teamName) {
        return callBookingRepository.findByTeamAndStatus(teamService.findByName(teamName), CallStatus.PENDING);
    }

    public CallBooking findByTeamAndMentor(Team team, User mentor){
        return callBookingRepository.findByTeamAndMentor(team, mentor)
                .orElseThrow(() -> new IllegalArgumentException("Nessuna call trovata con questo mentor per il tuo team"));
    }

    public CallBooking save(CallBooking callBooking) {
        return callBookingRepository.save(callBooking);
    }

    @Transactional
    public CallBooking cancelCall(Authentication authentication, String mentorUsername) {
        User leader = userService.findUserByUsername(authentication.getName());
        User mentor = userService.findUserByUsername(mentorUsername);

        CallBooking callBooking = findByTeamAndMentor(leader.getTeam(), mentor);

        callBooking.setStatus(CallStatus.CANCELLED);
        return save(callBooking);
    }

}
