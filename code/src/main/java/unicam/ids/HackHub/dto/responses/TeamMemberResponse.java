package unicam.ids.HackHub.dto.responses;

public record TeamMemberResponse(
        String username,
        String name,
        String surname,
        String email,
        String role
) {}