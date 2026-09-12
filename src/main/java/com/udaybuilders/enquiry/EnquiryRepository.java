package com.udaybuilders.enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> { long countByStatus(String status); }
