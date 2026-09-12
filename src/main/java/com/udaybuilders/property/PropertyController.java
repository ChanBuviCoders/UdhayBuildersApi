package com.udaybuilders.property;

import java.util.List;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private final PropertyRepository repository;

    public PropertyController(PropertyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Property> list(@RequestParam(required = false) String status,
                               @RequestParam(required = false) Boolean featured,
                               @RequestParam(required = false) String location,
                               @RequestParam(required = false) String type,
                               @RequestParam(required = false) BigDecimal minPrice,
                               @RequestParam(required = false) BigDecimal maxPrice,
                               @RequestParam(required = false) Integer bedrooms,
                               @RequestParam(required = false) BigDecimal minArea) {
        List<Property> result;
        if (status != null && !status.isBlank()) {
            result = repository.findByStatusIgnoreCaseOrderByIdDesc(status);
        } else if (Boolean.TRUE.equals(featured)) {
            result = repository.findByFeaturedTrueOrderByIdDesc();
        } else result = repository.findAll();
        // A property must have at least one uploaded image before it appears in the public card view.
        Stream<Property> stream = result.stream().filter(Property::hasImage);
        if (location != null && !location.isBlank()) stream = stream.filter(p -> p.getLocation().toLowerCase().contains(location.toLowerCase()));
        if (type != null && !type.isBlank()) stream = stream.filter(p -> p.getType().equalsIgnoreCase(type));
        if (minPrice != null) stream = stream.filter(p -> p.getSellingPrice().compareTo(minPrice) >= 0);
        if (maxPrice != null) stream = stream.filter(p -> p.getSellingPrice().compareTo(maxPrice) <= 0);
        if (bedrooms != null) stream = stream.filter(p -> p.getBedrooms() != null && p.getBedrooms() >= bedrooms);
        if (minArea != null) stream = stream.filter(p -> p.getArea() != null && p.getArea().compareTo(minArea) >= 0);
        return stream.toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> get(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Property create(@Valid @org.springframework.web.bind.annotation.RequestBody PropertyAdminRequest request) {
        return repository.save(new Property(request.name(), request.type(), request.location(), request.description(),
            request.area(), request.bedrooms(), request.bathrooms(), request.parkingAvailable(), request.status(),
            request.sellingPrice(), null, request.featured()));
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Property> update(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody PropertyAdminRequest request) {
        return repository.findById(id).map(property -> {
            property.update(request.name(), request.type(), request.location(), request.address(), request.description(),
                request.area(), request.builtUpArea(), request.landArea(), request.bedrooms(), request.bathrooms(),
                request.floors(), request.parkingAvailable(), request.constructionYear(), request.features(),
                request.status(), request.sellingPrice(), request.featured());
            return ResponseEntity.ok(repository.save(property));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id); return ResponseEntity.noContent().build();
    }
}
