package com.udaybuilders.media;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.PublicAccessType;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AzureBlobStorageService {
    private final String connectionString;
    private final String containerName;
    public AzureBlobStorageService(@Value("${app.azure.connection-string}") String connectionString,
                                   @Value("${app.azure.container}") String containerName) {
        this.connectionString=connectionString; this.containerName=containerName;
    }
    public StoredImage upload(String ownerType, Long ownerId, MultipartFile file, int order) throws IOException {
        if (connectionString == null || connectionString.isBlank()) throw new IllegalStateException("Azure Storage is not configured");
        String contentType = file.getContentType();
        if (contentType == null || !java.util.Set.of("image/jpeg","image/png","image/webp").contains(contentType))
            throw new IllegalArgumentException("Only JPG, PNG, and WebP images are supported");
        if (file.getSize() > 10 * 1024 * 1024) throw new IllegalArgumentException("Image exceeds 10MB limit");
        BlobContainerClient container = new BlobServiceClientBuilder().connectionString(connectionString)
            .buildClient().getBlobContainerClient(containerName);
        if (!container.exists()) {
            container.create();
        }
        try {
            // Card/detail images are public marketing assets, so the container must allow anonymous
            // read access; otherwise <img> tags on the public site get a 403 from Azure. Some storage
            // accounts disable public access entirely, so this best-effort call must not block uploads.
            container.setAccessPolicy(PublicAccessType.BLOB, null);
        } catch (RuntimeException ignored) {
        }
        String blobName = ownerType.toLowerCase() + "/" + ownerId + "/" + UUID.randomUUID() + "-" +
            file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
        BlobClient blob = container.getBlobClient(blobName);
        blob.upload(file.getInputStream(), file.getSize(), true);
        blob.setHttpHeaders(new BlobHttpHeaders().setContentType(contentType));
        return new StoredImage(ownerType, ownerId, file.getOriginalFilename(), blobName, blob.getBlobUrl(),
            contentType, file.getSize(), order);
    }
    public void delete(String blobName) {
        if (connectionString == null || connectionString.isBlank()) throw new IllegalStateException("Azure Storage is not configured");
        new BlobServiceClientBuilder().connectionString(connectionString).buildClient()
            .getBlobContainerClient(containerName).getBlobClient(blobName).deleteIfExists();
    }
}
