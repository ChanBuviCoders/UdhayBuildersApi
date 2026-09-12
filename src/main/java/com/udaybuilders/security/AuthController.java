package com.udaybuilders.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}

@RestController @RequestMapping("/api/v1/auth")
public class AuthController {
    private final AdminUserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
    public AuthController(AdminUserRepository users, PasswordEncoder encoder, JwtService jwt) {
        this.users = users; this.encoder = encoder; this.jwt = jwt;
    }
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        AdminUser user = users.findByEmailIgnoreCase(request.email())
            .filter(AdminUser::isEnabled).filter(candidate -> encoder.matches(request.password(), candidate.getPasswordHash()))
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        return Map.of("token", jwt.issue(user), "email", user.getEmail(), "role", user.getRole());
    }
}
