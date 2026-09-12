package com.udaybuilders.property;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.math.BigDecimal;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String location;
    private String address;
    private BigDecimal builtUpArea;
    private BigDecimal landArea;
    private String description;
    private BigDecimal area;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer floors;
    private boolean parkingAvailable;
    private Integer constructionYear;
    @Column(length = 2000)
    private String features;
    private String status;
    private BigDecimal sellingPrice;
    private String mainImageUrl;
    private boolean featured;
    private java.time.Instant createdAt;
    private java.time.Instant updatedAt;

    protected Property() {
    }

    public Property(String name, String type, String location, String description, BigDecimal area,
                    Integer bedrooms, Integer bathrooms, boolean parkingAvailable, String status,
                    BigDecimal sellingPrice, String mainImageUrl, boolean featured) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.description = description;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.parkingAvailable = parkingAvailable;
        this.status = status;
        this.sellingPrice = sellingPrice;
        this.mainImageUrl = mainImageUrl;
        this.featured = featured;
    }

    @jakarta.persistence.PrePersist void onCreate() { createdAt = updatedAt = java.time.Instant.now(); }
    @jakarta.persistence.PreUpdate void onUpdate() { updatedAt = java.time.Instant.now(); }
    public void update(String name, String type, String location, String address, String description,
                       BigDecimal area, BigDecimal builtUpArea, BigDecimal landArea, Integer bedrooms,
                       Integer bathrooms, Integer floors, boolean parkingAvailable, Integer constructionYear,
                       String features, String status, BigDecimal sellingPrice, boolean featured) {
        this.name=name; this.type=type; this.location=location; this.address=address; this.description=description;
        this.area=area; this.builtUpArea=builtUpArea; this.landArea=landArea; this.bedrooms=bedrooms;
        this.bathrooms=bathrooms; this.floors=floors; this.parkingAvailable=parkingAvailable;
        this.constructionYear=constructionYear; this.features=features; this.status=status;
        this.sellingPrice=sellingPrice; this.featured=featured;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getLocation() { return location; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public BigDecimal getArea() { return area; }
    public BigDecimal getBuiltUpArea() { return builtUpArea; }
    public BigDecimal getLandArea() { return landArea; }
    public Integer getBedrooms() { return bedrooms; }
    public Integer getBathrooms() { return bathrooms; }
    public Integer getFloors() { return floors; }
    public boolean isParkingAvailable() { return parkingAvailable; }
    public Integer getConstructionYear() { return constructionYear; }
    public String getFeatures() { return features; }
    public String getStatus() { return status; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }
    public boolean hasImage() { return mainImageUrl != null && !mainImageUrl.isBlank(); }
    public boolean isFeatured() { return featured; }
    public java.time.Instant getCreatedAt() { return createdAt; }
    public java.time.Instant getUpdatedAt() { return updatedAt; }
}
