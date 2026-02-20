package unicam.ids.HackHub.scheduler;

import unicam.ids.HackHub.enums.InviteState;
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
        LocalDateTime now = LocalDateTime.now();

        var expiredOutside = outsideInviteRepository
                .findExpiredInvites(InviteState.PENDING, now);

        expiredOutside.forEach(invite -> invite.setStatus(InviteState.EXPIRED));
        outsideInviteRepository.saveAll(expiredOutside);

        var expiredTeam = insideInviteRepository
                .findExpiredInvites(InviteState.PENDING, now);

        expiredTeam.forEach(invite -> invite.setStatus(InviteState.EXPIRED));
        insideInviteRepository.saveAll(expiredTeam);
    }
}