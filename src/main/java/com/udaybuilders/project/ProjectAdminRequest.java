package com.udaybuilders.project;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectAdminRequest(
    @NotBlank String name, @NotBlank String type, String category, @NotBlank String location,
    @NotBlank String description, String additionalFeatures, @NotBlank String status,
    @NotNull @Positive BigDecimal area, @PositiveOrZero Integer floors, @PositiveOrZero Integer bedrooms,
    @PositiveOrZero Integer bathrooms, LocalDate startDate, LocalDate completionDate,
    @PositiveOrZero BigDecimal estimatedConstructionCost, @PositiveOrZero BigDecimal finalConstructionCost,
    @PositiveOrZero BigDecimal sellingPrice, @PositiveOrZero BigDecimal otherExpenses,
    boolean showFinancialsPublicly, boolean featured
) {}
