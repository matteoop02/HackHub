package unicam.ids.HackHub.service;

import unicam.ids.HackHub.model.InviteInsidePlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendTeamInviteNotification(InviteInsidePlatform invite) {
        log.info("=== Notifica Invito Team ===");
        log.info("Utente: {}", invite.getRecipientUsername());
        log.info("Team: {}", invite.getTeamName());
        log.info("Ruolo: {}", invite.getProposedRole());
        log.info("Da: {}", invite.getSenderUsername());
        log.info("===========================");
    }

    public void notifyInviteAccepted(InviteInsidePlatform invite) {
        log.info("Notifica: {} ha accettato l'invito al team {}",
                invite.getRecipientUsername(), invite.getTeamName());
    }

    public void notifyInviteRejected(InviteInsidePlatform invite) {
        log.info("Notifica: {} ha rifiutato l'invito al team {}",
                invite.getRecipientUsername(), invite.getTeamName());
    }
}