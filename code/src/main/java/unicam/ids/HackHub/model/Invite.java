package unicam.ids.HackHub.model;

import java.time.LocalDateTime;
import unicam.ids.HackHub.enums.InviteStatus;

public interface Invite {
    Long getId();
    User getSenderUser();
    InviteStatus getStatus();
    LocalDateTime getCreatedAt();
    LocalDateTime getExpiresAt();

    void send();
    void accept();
    void reject();
    void cancel();
    boolean isExpired();
}