package com.fomi.domain.auth.entity;
import jakarta.persistence.*;
import com.fomi.domain.profile.entity.User;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="auth_sessions")
public class AuthSession {
 @Id private UUID id;
 @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="user_id") private User user;
 @Column(name="access_hash", nullable=false, unique=true, length=64) private String accessHash;
 @Column(name="refresh_hash", nullable=false, unique=true, length=64) private String refreshHash;
 @Column(name="access_expires_at", nullable=false) private Instant accessExpiresAt;
 @Column(name="refresh_expires_at", nullable=false) private Instant refreshExpiresAt;
 protected AuthSession() {}
 public AuthSession(User user, Instant refreshExpiry) { id=UUID.randomUUID(); this.user=user; refreshExpiresAt=refreshExpiry; }
 public void rotate(String access, String refresh, Instant expiry) { accessHash=access; refreshHash=refresh; accessExpiresAt=expiry; }
 public User getUser() { return user; }
 public Instant getAccessExpiresAt() { return accessExpiresAt; }
 public Instant getRefreshExpiresAt() { return refreshExpiresAt; }
}
