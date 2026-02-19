package unicam.ids.HackHub.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import unicam.ids.HackHub.enums.HackathonStatus;
import unicam.ids.HackHub.model.Hackathon;
import unicam.ids.HackHub.service.HackathonService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HackathonStateScheduler {

    private final HackathonService hackathonService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void updateHackathonStates() {
        LocalDateTime now = LocalDateTime.now();

        subscriptionToStartHackathons(); // IN_ISCRIZIONE passa ad IN_CORSO
        startToEvaluationHackathons(); // IN_CORSO passa ad IN_VALUTAZIONE
    }

    public void subscriptionToStartHackathons() {
        changeState(HackathonStatus.IN_ISCRIZIONE);
    }

    public void startToEvaluationHackathons() {
        changeState(HackathonStatus.IN_CORSO);
    }

    public void changeState(HackathonStatus status) {
        List<Hackathon> hackathons = hackathonService.getHackathons();

        for (Hackathon h : hackathons) {
            if (status.equals(h.getState()) && h.getStartDate().equals(LocalDateTime.now()))
                h.setState(HackathonStatus.IN_CORSO);

            if (status.equals(h.getState()) && h.getEndDate().equals(LocalDateTime.now()))
                h.setState(HackathonStatus.IN_VALUTAZIONE);
        }

        hackathonService.saveAll(hackathons);
    }
}