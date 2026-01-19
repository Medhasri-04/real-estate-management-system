package com.realestatemanagement.dto.response;

public class FavoritePropertyResponse {
	private Long propertyId;
	private String title;
	private String city;

	public FavoritePropertyResponse() {
	}

	public FavoritePropertyResponse(Long propertyId, String title, String city) {
		this.propertyId = propertyId;
		this.title = title;
		this.city = city;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}