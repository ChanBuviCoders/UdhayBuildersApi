package com.udaybuilders.media;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StoredImageRepository extends JpaRepository<StoredImage, Long> {
    List<StoredImage> findByOwnerTypeAndOwnerIdOrderByDisplayOrderAsc(String ownerType, Long ownerId);
}