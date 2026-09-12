package com.udaybuilders.enquiry;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/enquiries")
public class EnquiryController {
    private final EnquiryRepository repository;
    public EnquiryController(EnquiryRepository repository) { this.repository = repository; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Enquiry create(@Valid @RequestBody EnquiryRequest request) {
        return repository.save(new Enquiry(request));
    }

    @GetMapping("/admin")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public List<Enquiry> list() { return repository.findAll(); }

    @PutMapping("/admin/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Enquiry> update(@PathVariable Long id, @RequestBody Map<String,String> update) {
        return repository.findById(id).map(enquiry -> {
            enquiry.update(update.get("status"), update.get("adminRemarks"));
            return ResponseEntity.ok(repository.save(enquiry));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
