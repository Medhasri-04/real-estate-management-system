package com.realestatemanagement.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class SiteVisitRequestDTO {

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Visit date and time is required")
    private LocalDateTime visitDateTime;

    private String status = "PENDING"; // default status

    // Getters & Setters
    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public LocalDateTime getVisitDateTime() {
        return visitDateTime;
    }

    public void setVisitDateTime(LocalDateTime visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
