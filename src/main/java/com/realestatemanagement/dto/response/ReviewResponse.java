package com.realestatemanagement.dto.response;

import java.time.LocalDateTime;

public class ReviewResponse {
	private Long reviewId;
	private Integer rating;
	private String comment;
	private LocalDateTime createdAt;

	public ReviewResponse() {
	}

	public ReviewResponse(Long reviewId, Integer rating, String comment, LocalDateTime createdAt) {
		this.reviewId = reviewId;
		this.rating = rating;
		this.comment = comment;
		this.createdAt = createdAt;
	}

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}