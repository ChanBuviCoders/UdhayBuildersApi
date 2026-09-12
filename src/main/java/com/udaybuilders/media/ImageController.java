package com.udaybuilders.media;

import java.io.IOException;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.udaybuilders.project.ProjectRepository;
import com.udaybuilders.property.PropertyRepository;

@RestController
public class ImageController {
    private final StoredImageRepository images; private final AzureBlobStorageService storage;
    private final ProjectRepository projects; private final PropertyRepository properties;
    public ImageController(StoredImageRepository images, AzureBlobStorageService storage,
                           ProjectRepository projects, PropertyRepository properties) {
        this.images=images; this.storage=storage; this.projects=projects; this.properties=properties;
    }
    @GetMapping("/api/v1/{ownerType:projects|properties}/{ownerId}/images")
    public List<StoredImage> list(@PathVariable String ownerType, @PathVariable Long ownerId) {
        return images.findByOwnerTypeAndOwnerIdOrderByDisplayOrderAsc(ownerType.equals("projects") ? "PROJECT" : "PROPERTY", ownerId);
    }
    @PostMapping("/api/v1/{ownerType:projects|properties}/{ownerId}/images")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public StoredImage upload(@PathVariable String ownerType, @PathVariable Long ownerId,
                              @RequestParam("file") MultipartFile file, @RequestParam(defaultValue="0") int order) throws IOException {
        String type = ownerType.equals("projects") ? "PROJECT" : "PROPERTY";
        StoredImage saved = images.save(storage.upload(type, ownerId, file, order));
        // The first image uploaded for an owner automatically becomes its mandatory main/card image.
        Long savedId = saved.getId();
        boolean anotherImageIsAlreadyMain = images.findByOwnerTypeAndOwnerIdOrderByDisplayOrderAsc(type, ownerId).stream()
            .anyMatch(i -> !i.getId().equals(savedId) && i.isMainImage());
        if (!anotherImageIsAlreadyMain) {
            saved.setMainImage(true);
            saved = images.save(saved);
            applyMainImageUrl(ownerType, ownerId, saved.getBlobUrl());
        }
        return saved;
    }
    @DeleteMapping("/api/v1/images/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        StoredImage image = images.findById(id).orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        boolean wasMain = image.isMainImage();
        String ownerType = image.getOwnerType(); Long ownerId = image.getOwnerId();
        storage.delete(image.getBlobName()); images.delete(image);
        if (wasMain) {
            String pathOwnerType = ownerType.equals("PROJECT") ? "projects" : "properties";
            List<StoredImage> remaining = images.findByOwnerTypeAndOwnerIdOrderByDisplayOrderAsc(ownerType, ownerId);
            if (remaining.isEmpty()) {
                applyMainImageUrl(pathOwnerType, ownerId, null);
            } else {
                StoredImage next = remaining.get(0);
                next.setMainImage(true); images.save(next);
                applyMainImageUrl(pathOwnerType, ownerId, next.getBlobUrl());
            }
        }
    }

    private void applyMainImageUrl(String pathOwnerType, Long ownerId, String url) {
        if (pathOwnerType.equals("projects")) {
            projects.findById(ownerId).ifPresent(project -> { project.setMainImageUrl(url); projects.save(project); });
        } else {
            properties.findById(ownerId).ifPresent(property -> { property.setMainImageUrl(url); properties.save(property); });
        }
    }
}