package com.udaybuilders.project;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectAdminView(Long id, String name, String type, String category, String location, String description,
    String additionalFeatures, String status, BigDecimal area, Integer floors, Integer bedrooms, Integer bathrooms,
    LocalDate startDate, LocalDate completionDate, BigDecimal estimatedConstructionCost,
    BigDecimal finalConstructionCost, BigDecimal sellingPrice, BigDecimal otherExpenses,
    BigDecimal profit, boolean showFinancialsPublicly, boolean featured, boolean archived) {
    public static ProjectAdminView of(Project p) {
        return new ProjectAdminView(p.getId(), p.getName(), p.getType(), p.getCategory(), p.getLocation(), p.getDescription(),
            p.getAdditionalFeatures(), p.getStatus(), p.getArea(), p.getFloors(), p.getBedrooms(), p.getBathrooms(),
            p.getStartDate(), p.getCompletionDate(), p.getEstimatedConstructionCost(), p.getFinalConstructionCost(),
            p.getSellingPrice(), p.getOtherExpenses(), p.getProfit(), p.isShowFinancialsPublicly(), p.isFeatured(), p.isArchived());
    }
}
