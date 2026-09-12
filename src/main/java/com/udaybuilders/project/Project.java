package com.udaybuilders.project;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String category;
    private String location;
    @Column(length = 4000)
    private String description;
    @Column(length = 2000)
    private String additionalFeatures;
    private String status;
    private BigDecimal area;
    private Integer floors;
    private Integer bedrooms;
    private Integer bathrooms;
    private LocalDate startDate;
    private LocalDate completionDate;
    private BigDecimal sellingPrice;
    @JsonIgnore private BigDecimal estimatedConstructionCost;
    @JsonIgnore private BigDecimal finalConstructionCost;
    @JsonIgnore private BigDecimal otherExpenses;
    private boolean showFinancialsPublicly;
    private String mainImageUrl;
    private boolean featured;
    private boolean archived;
    private java.time.Instant createdAt;
    private java.time.Instant updatedAt;

    protected Project() {
    }

    public Project(String name, String type, String location, String description, String status,
                   BigDecimal area, Integer floors, Integer bedrooms, Integer bathrooms,
                   LocalDate startDate, LocalDate completionDate, BigDecimal sellingPrice,
                   String mainImageUrl, boolean featured) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.description = description;
        this.status = status;
        this.area = area;
        this.floors = floors;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.startDate = startDate;
        this.completionDate = completionDate;
        this.sellingPrice = sellingPrice;
        this.mainImageUrl = mainImageUrl;
        this.featured = featured;
    }

    @PrePersist void onCreate() { createdAt = updatedAt = java.time.Instant.now(); }
    @PreUpdate void onUpdate() { updatedAt = java.time.Instant.now(); }
    public void update(String name, String type, String category, String location, String description,
                       String additionalFeatures, String status, BigDecimal area, Integer floors,
                       Integer bedrooms, Integer bathrooms, LocalDate startDate, LocalDate completionDate,
                       BigDecimal estimatedConstructionCost, BigDecimal finalConstructionCost,
                       BigDecimal sellingPrice, BigDecimal otherExpenses, boolean showFinancialsPublicly,
                       boolean featured) {
        this.name=name; this.type=type; this.category=category; this.location=location; this.description=description;
        this.additionalFeatures=additionalFeatures; this.status=status; this.area=area; this.floors=floors;
        this.bedrooms=bedrooms; this.bathrooms=bathrooms; this.startDate=startDate; this.completionDate=completionDate;
        this.estimatedConstructionCost=estimatedConstructionCost; this.finalConstructionCost=finalConstructionCost;
        this.sellingPrice=sellingPrice; this.otherExpenses=otherExpenses; this.showFinancialsPublicly=showFinancialsPublicly;
        this.featured=featured;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getAdditionalFeatures() { return additionalFeatures; }
    public String getStatus() { return status; }
    public BigDecimal getArea() { return area; }
    public Integer getFloors() { return floors; }
    public Integer getBedrooms() { return bedrooms; }
    public Integer getBathrooms() { return bathrooms; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getCompletionDate() { return completionDate; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public BigDecimal getEstimatedConstructionCost() { return estimatedConstructionCost; }
    public BigDecimal getFinalConstructionCost() { return finalConstructionCost; }
    public BigDecimal getOtherExpenses() { return otherExpenses; }
    public boolean isShowFinancialsPublicly() { return showFinancialsPublicly; }
    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }
    public boolean hasImage() { return mainImageUrl != null && !mainImageUrl.isBlank(); }
    public boolean isFeatured() { return featured; }
    public boolean isArchived() { return archived; }
    public java.time.Instant getCreatedAt() { return createdAt; }
    public java.time.Instant getUpdatedAt() { return updatedAt; }
    @JsonIgnore public BigDecimal getProfit() {
        if (sellingPrice == null || finalConstructionCost == null) return null;
        return sellingPrice.subtract(finalConstructionCost).subtract(otherExpenses == null ? BigDecimal.ZERO : otherExpenses);
    }
}
