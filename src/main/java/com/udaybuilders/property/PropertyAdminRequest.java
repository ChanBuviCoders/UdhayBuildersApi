package com.udaybuilders.property;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PropertyAdminRequest(
    @NotBlank String name, @NotBlank String type, @NotBlank String location, String address,
    @NotBlank String description, @NotNull @Positive BigDecimal area, @Positive BigDecimal builtUpArea,
    @Positive BigDecimal landArea, @PositiveOrZero Integer bedrooms, @PositiveOrZero Integer bathrooms,
    @PositiveOrZero Integer floors, boolean parkingAvailable, @PositiveOrZero Integer constructionYear,
    String features, @NotBlank String status, @NotNull @PositiveOrZero BigDecimal sellingPrice, boolean featured
) {}
