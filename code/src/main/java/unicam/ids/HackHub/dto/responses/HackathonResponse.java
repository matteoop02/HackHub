package unicam.ids.HackHub.dto.responses;

import lombok.Builder;
import unicam.ids.HackHub.enums.HackathonState;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import java.util.Set;
import java.time.LocalDateTime;

@Builder
public record HackathonResponse(
    Long id,
    String name,
    String place,
    String regulation,
    LocalDateTime subscriptionDeadline,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Double reward,
    Integer maxTeamSize,
    boolean isPublic,
    HackathonState state,
    String organizerName,
    Set<User> mentors,
    User judge,
    Set<Team> teams,
    Long winningTeamId,
    String winningTeamName) {
}
