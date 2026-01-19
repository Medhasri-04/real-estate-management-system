package com.realestatemanagement.dto.response;

import java.util.Map;

public class ReviewSummaryResponse {
	private Long propertyId;
	private Double averageRating;
	private int totalReviews;
	private Map<String, Integer> breakdown;

	public ReviewSummaryResponse() {
	}

	public ReviewSummaryResponse(Long propertyId, Double averageRating, int totalReviews,
			Map<String, Integer> breakdown) {
		this.propertyId = propertyId;
		this.averageRating = averageRating;
		this.totalReviews = totalReviews;
		this.breakdown = breakdown;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public int getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(int totalReviews) {
		this.totalReviews = totalReviews;
	}

	public Map<String, Integer> getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(Map<String, Integer> breakdown) {
		this.breakdown = breakdown;
	}
}