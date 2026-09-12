package com.udaybuilders.project;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatusIgnoreCaseOrderByIdDesc(String status);
    List<Project> findByFeaturedTrueOrderByIdDesc();
    long countByStatusIgnoreCase(String status);
}
