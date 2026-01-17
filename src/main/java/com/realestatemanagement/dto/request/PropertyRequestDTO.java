package com.realestatemanagement.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PropertyRequestDTO {
	@NotBlank(message = "Title is required")
	private String title;
	@NotBlank(message = "Description is required")
	private String description;
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be positive")
	private Double price;
	@NotBlank(message = "Location is required")
	private String location;
	@NotBlank(message = "Property type is required")
	private String propertyType; // RENT or SALE as String
	@NotNull(message = "Availability is required")
	private Boolean availability;
	@Positive(message = "Property age must be positive")
	private Integer propertyAge;
	@NotBlank(message = "Furnishing status is required")
	private String furnishingStatus;
	@NotNull(message = "Latitude is required")
	private Double latitude;
	@NotNull(message = "Longitude is required")
	private Double longitude;
	@NotNull(message = "Agent ID is required")
	private Long agentId;
	private Set<Long> amenityIds;

	// Getters & Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Integer getPropertyAge() {
		return propertyAge;
	}

	public void setPropertyAge(Integer propertyAge) {
		this.propertyAge = propertyAge;
	}

	public String getFurnishingStatus() {
		return furnishingStatus;
	}

	public void setFurnishingStatus(String furnishingStatus) {
		this.furnishingStatus = furnishingStatus;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Set<Long> getAmenityIds() {
		return amenityIds;
	}

	public void setAmenityIds(Set<Long> amenityIds) {
		this.amenityIds = amenityIds;
	}
}
