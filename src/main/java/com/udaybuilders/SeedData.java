package com.udaybuilders;

import com.udaybuilders.project.Project;
import com.udaybuilders.project.ProjectRepository;
import com.udaybuilders.property.Property;
import com.udaybuilders.property.PropertyRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.udaybuilders.security.AdminUser;
import com.udaybuilders.security.AdminUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedData {
    @Bean
    CommandLineRunner seed(ProjectRepository projects, PropertyRepository properties,
                           AdminUserRepository users, PasswordEncoder encoder) {
        return args -> {
            String adminEmail = System.getenv().getOrDefault("ADMIN_EMAIL", "admin@udaybuilders.in");
            String adminPassword = System.getenv().getOrDefault("ADMIN_PASSWORD", "ChangeMe!2026");
            if (users.findByEmailIgnoreCase(adminEmail).isEmpty()) users.save(new AdminUser(adminEmail, encoder.encode(adminPassword)));
            if (projects.count() == 0) {
                projects.save(new Project("Palm Grove Residence", "Independent House", "Chennai",
                    "A light-filled family home designed around a private garden.", "COMPLETED",
                    new BigDecimal("2400"), 2, 3, 3, LocalDate.of(2023, 1, 10),
                    LocalDate.of(2024, 2, 15), null,
                    null, true));
                projects.save(new Project("Harbour View Villas", "Villa", "ECR, Chennai",
                    "Contemporary villas with generous outdoor living and premium finishes.", "ONGOING",
                    new BigDecimal("3200"), 2, 4, 4, LocalDate.of(2024, 8, 1),
                    null, null,
                    null, true));
            }
            if (properties.count() == 0) {
                properties.save(new Property("Greenfield Villa", "Villa", "OMR, Chennai",
                    "Move-in ready villa in a connected, peaceful neighbourhood.", new BigDecimal("1850"),
                    3, 3, true, "AVAILABLE", new BigDecimal("12500000"),
                    null, true));
                properties.save(new Property("Lakeview Apartment", "Apartment", "Velachery, Chennai",
                    "Modern apartment with natural light and clubhouse access.", new BigDecimal("1200"),
                    2, 2, true, "AVAILABLE", new BigDecimal("7800000"),
                    null, true));
            }
        };
    }
}
