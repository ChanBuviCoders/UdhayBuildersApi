package com.udaybuilders.company;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    @GetMapping
    public Map<String, Object> getProfile() {
        return Map.of(
            "name", "Uday Builders",
            "tagline", "Building spaces. Creating futures.",
            "description", "Trusted construction and property specialists delivering thoughtful spaces with lasting quality.",
            "phone", "+91 98765 43210",
            "email", "hello@udaybuilders.in",
            "location", "Chennai, Tamil Nadu",
            "services", new String[]{"Residential construction", "Commercial projects", "Property sales"}
        );
    }
}
