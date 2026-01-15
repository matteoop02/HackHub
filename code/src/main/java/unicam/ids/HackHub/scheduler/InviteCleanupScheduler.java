package unicam.ids.HackHub.scheduler;

import unicam.ids.HackHub.enums.InviteStatus;
import unicam.ids.HackHub.repository.OutsideInviteRepository;
import unicam.ids.HackHub.repository.InsideInviteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class InviteCleanupScheduler {

    private final OutsideInviteRepository outsideInviteRepository;
    private final InsideInviteRepository insideInviteRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void cleanupExpiredInvites() {
        log.info("Avvio pulizia inviti scaduti");

        LocalDateTime now = LocalDateTime.now();

        var expiredOutside = outsideInviteRepository
                .findExpiredInvites(InviteStatus.PENDING, now);

        expiredOutside.forEach(invite -> invite.setStatus(InviteStatus.EXPIRED));
        outsideInviteRepository.saveAll(expiredOutside);

        var expiredTeam = insideInviteRepository
                .findExpiredInvites(InviteStatus.PENDING, now);

        expiredTeam.forEach(invite -> invite.setStatus(InviteStatus.EXPIRED));
        insideInviteRepository.saveAll(expiredTeam);

        log.info("Pulizia completata - Esterni: {}, Team: {}",
                expiredOutside.size(), expiredTeam.size());
    }
}