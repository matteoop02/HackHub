package unicam.ids.HackHub.dto.requests.invite;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AcceptTeamInvite(
     @NotNull
     Long inviteId
) {}
