package com.udaybuilders.security;

import jakarta.persistence.*;

@Entity
@Table(name = "users", indexes = @Index(name = "ix_users_email", columnList = "email", unique = true))
public class AdminUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false, length = 30)
    private String role = "ADMIN";
    private boolean enabled = true;

    protected AdminUser() {}
    public AdminUser(String email, String passwordHash) { this.email = email; this.passwordHash = passwordHash; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getRole() { return role; }
    public boolean isEnabled() { return enabled; }
}
