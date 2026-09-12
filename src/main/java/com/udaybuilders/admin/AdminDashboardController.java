package com.udaybuilders.admin;

import com.udaybuilders.enquiry.EnquiryRepository;
import com.udaybuilders.project.ProjectRepository;
import com.udaybuilders.property.PropertyRepository;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/v1/admin/dashboard")
public class AdminDashboardController {
    private final ProjectRepository projects; private final PropertyRepository properties; private final EnquiryRepository enquiries;
    public AdminDashboardController(ProjectRepository projects, PropertyRepository properties, EnquiryRepository enquiries) {
        this.projects=projects; this.properties=properties; this.enquiries=enquiries;
    }
    @GetMapping @PreAuthorize("hasRole('ADMIN')")
    public Map<String,Long> dashboard() {
        return Map.of("totalProjects", projects.count(), "completedProjects", projects.countByStatusIgnoreCase("COMPLETED"),
            "ongoingProjects", projects.countByStatusIgnoreCase("ONGOING"), "totalProperties", properties.count(),
            "availableProperties", properties.countByStatusIgnoreCase("AVAILABLE"), "soldProperties", properties.countByStatusIgnoreCase("SOLD"),
            "totalEnquiries", enquiries.count(), "newEnquiries", enquiries.countByStatus("NEW"));
    }
}
