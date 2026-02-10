package unicam.ids.HackHub.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import unicam.ids.HackHub.service.HackathonService;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class HackathonStateScheduler {

    private final HackathonService hackathonService;

    @Scheduled(fixedRate = 60_000) // Esegue ogni minuto ma volendo lo posso cambiare,per ora però lo lascio
                                   // così,poi vediamo
    public void updateHackathonStates() {
        LocalDateTime now = LocalDateTime.now();

        hackathonService.startHackathons(now); // IN_ISCRIZIONE passa ad IN_CORSO
        hackathonService.moveHackathonsToEvaluation(now); // IN_CORSO passa ad IN_VALUTAZIONE
    }
}