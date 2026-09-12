package com.udaybuilders.project;

import java.util.List;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Project> list(@RequestParam(required = false) String status,
                              @RequestParam(required = false) Boolean featured) {
        List<Project> result;
        if (status != null && !status.isBlank()) {
            result = repository.findByStatusIgnoreCaseOrderByIdDesc(status);
        } else if (Boolean.TRUE.equals(featured)) {
            result = repository.findByFeaturedTrueOrderByIdDesc();
        } else {
            result = repository.findAll();
        }
        // A project must have at least one uploaded image before it appears in the public card view.
        return result.stream().filter(Project::hasImage).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id));
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@Valid @org.springframework.web.bind.annotation.RequestBody ProjectAdminRequest request) {
        Project project = new Project(request.name(), request.type(), request.location(), request.description(),
            request.status(), request.area(), request.floors(), request.bedrooms(), request.bathrooms(),
            request.startDate(), request.completionDate(), request.sellingPrice(), null, request.featured());
        project.update(request.name(), request.type(), request.category(), request.location(), request.description(),
            request.additionalFeatures(), request.status(), request.area(), request.floors(), request.bedrooms(),
            request.bathrooms(), request.startDate(), request.completionDate(), request.estimatedConstructionCost(),
            request.finalConstructionCost(), request.sellingPrice(), request.otherExpenses(),
            request.showFinancialsPublicly(), request.featured());
        return repository.save(project);
    }

    @PutMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> update(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody ProjectAdminRequest request) {
        return repository.findById(id).map(project -> {
            project.update(request.name(), request.type(), request.category(), request.location(), request.description(),
                request.additionalFeatures(), request.status(), request.area(), request.floors(), request.bedrooms(),
                request.bathrooms(), request.startDate(), request.completionDate(), request.estimatedConstructionCost(),
                request.finalConstructionCost(), request.sellingPrice(), request.otherExpenses(),
                request.showFinancialsPublicly(), request.featured());
            return ResponseEntity.ok(repository.save(project));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> archive(@PathVariable Long id) {
        return repository.findById(id).map(project -> {
            project.update(project.getName(), project.getType(), project.getCategory(), project.getLocation(),
                project.getDescription(), project.getAdditionalFeatures(), "ARCHIVED", project.getArea(),
                project.getFloors(), project.getBedrooms(), project.getBathrooms(), project.getStartDate(),
                project.getCompletionDate(), project.getEstimatedConstructionCost(), project.getFinalConstructionCost(),
                project.getSellingPrice(), project.getOtherExpenses(), project.isShowFinancialsPublicly(), false);
            repository.save(project); return ResponseEntity.noContent().<Void>build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/admin")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public List<ProjectAdminView> adminList() {
        return repository.findAll().stream().map(ProjectAdminView::of).collect(Collectors.toList());
    }

    @GetMapping("/admin/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectAdminView> adminGet(@PathVariable Long id) {
        return repository.findById(id).map(ProjectAdminView::of).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
