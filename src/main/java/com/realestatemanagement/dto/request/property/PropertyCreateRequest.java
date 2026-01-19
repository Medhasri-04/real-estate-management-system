package com.realestatemanagement.dto.request.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PropertyCreateRequest {
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	@NotNull
	private Double price;
	@NotBlank
	private String location;
	@NotBlank
	private String propertyType; // RENT / SALE
	@NotNull
	private Boolean availability;
	private Integer propertyAge;
	@NotBlank
	private String furnishingStatus;
	@NotNull
	private Double latitude;
	@NotNull
	private Double longitude;

	public PropertyCreateRequest() {
	}

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
}