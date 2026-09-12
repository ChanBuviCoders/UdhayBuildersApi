package com.udaybuilders.enquiry;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EnquiryRequest(
    @NotBlank String customerName,
    @NotBlank String mobileNumber,
    @Email String email,
    Long projectId,
    Long propertyId,
    @NotBlank String message
) {}
