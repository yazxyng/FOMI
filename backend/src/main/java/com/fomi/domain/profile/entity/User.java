package com.fomi.domain.profile.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_users_username", columnNames = "username"))
public class User {
    @Id private UUID id;
    @Column(nullable = false, length = 20) private String username;
    @Column(nullable = false, length = 12) private String nickname;
    @Column(name = "password_hash", nullable = false, length = 255) private String passwordHash;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    protected User() {}
    public User(String username, String nickname, String passwordHash) {
        this.id = UUID.randomUUID(); this.username = username; this.nickname = nickname;
        this.passwordHash = passwordHash; this.createdAt = Instant.now(); this.updatedAt = createdAt;
    }
    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getNickname() { return nickname; }
    public String getPasswordHash() { return passwordHash; }
}
