package com.udaybuilders.property;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByStatusIgnoreCaseOrderByIdDesc(String status);
    List<Property> findByFeaturedTrueOrderByIdDesc();
    long countByStatusIgnoreCase(String status);
}
