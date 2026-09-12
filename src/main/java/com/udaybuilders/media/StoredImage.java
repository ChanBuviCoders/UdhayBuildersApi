package com.udaybuilders.media;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "images", indexes = @Index(name = "ix_images_owner", columnList = "ownerType,ownerId"))
public class StoredImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, length=30) private String ownerType;
    @Column(nullable=false) private Long ownerId;
    @Column(nullable=false) private String fileName;
    @Column(nullable=false) private String blobName;
    @Column(nullable=false) private String blobUrl;
    @Column(nullable=false, length=100) private String contentType;
    @Column(nullable=false) private long size;
    private boolean mainImage;
    private int displayOrder;
    private Instant createdAt;
    protected StoredImage() {}
    public StoredImage(String ownerType, Long ownerId, String fileName, String blobName, String blobUrl,
                       String contentType, long size, int displayOrder) {
        this.ownerType=ownerType; this.ownerId=ownerId; this.fileName=fileName; this.blobName=blobName;
        this.blobUrl=blobUrl; this.contentType=contentType; this.size=size; this.displayOrder=displayOrder;
        this.createdAt=Instant.now();
    }
    public Long getId(){return id;} public String getOwnerType(){return ownerType;} public Long getOwnerId(){return ownerId;}
    public String getFileName(){return fileName;} public String getBlobName(){return blobName;} public String getBlobUrl(){return blobUrl;}
    public String getContentType(){return contentType;} public long getSize(){return size;} public boolean isMainImage(){return mainImage;}
    public int getDisplayOrder(){return displayOrder;}
    public void setMainImage(boolean mainImage){this.mainImage = mainImage;}
}