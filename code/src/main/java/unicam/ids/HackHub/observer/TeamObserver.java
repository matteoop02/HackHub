package unicam.ids.HackHub.observer;

import org.springframework.beans.factory.annotation.Autowired;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.model.Team;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.service.EmailService;

public class TeamObserver implements HackathonObserver {
    private final Team team;

    @Autowired
    EmailService emailService;

    public TeamObserver(Team team) {
        this.team = team;
    }

    @Override
    public void update(Hackathon hackathon, String subject, String message) {
        for (User user : team.getMembers()) {
            emailService.sendEmail(user.getEmail(), hackathon.getOrganizer().getEmail(), subject, message);
        }
    }
}
