package unicam.ids.HackHub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import unicam.ids.HackHub.enums.InviteState;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "team_invites", indexes = {
        @Index(name = "idx_recipient_user", columnList = "recipient_user_id"),
        @Index(name = "idx_team_id", columnList = "team_id"),
        @Index(name = "idx_status", columnList = "status")
})
public class InviteInsidePlatform implements Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_user_id", nullable = false)
    private Long senderId;

    @Column(name = "sender_username", nullable = false)
    private String senderUsername;

    @Column(name = "recipient_user_id", nullable = false)
    private Long recipientUserId;

    @Column(name = "recipient_username", nullable = false)
    private String recipientUsername;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @ManyToOne
    @JoinColumn(name = "RoleId", nullable = false)
    private UserRole proposedRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InviteState status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(length = 1000)
    private String message;

    @Override
    public void send() {
        if (this.status != InviteState.PENDING) {
            throw new IllegalStateException("L'invito può essere inviato solo se è in stato PENDING");
        }
    }

    @Override
    public void accept() {
        if (isExpired()) {
            throw new IllegalStateException("L'invito è scaduto");
        }
        if (this.status != InviteState.PENDING) {
            throw new IllegalStateException("Solo gli inviti PENDING possono essere accettati");
        }
        this.status = InviteState.ACCEPTED;
    }

    @Override
    public void reject() {
        if (this.status != InviteState.PENDING) {
            throw new IllegalStateException("Solo gli inviti PENDING possono essere rifiutati");
        }
        this.status = InviteState.REJECTED;
    }

    @Override
    public void cancel() {
        if (this.status != InviteState.PENDING) {
            throw new IllegalStateException("Solo gli inviti PENDING possono essere cancellati");
        }
        this.status = InviteState.CANCELLED;
    }

    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt) && status == InviteState.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}