package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.response.ReviewResponseDTO;
import com.realestatemanagement.service.ReviewService;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	// Create a review
	@PostMapping("/property/{propertyId}/user/{userId}")
	public ResponseEntity<ReviewResponseDTO> createReview(@PathVariable Long propertyId, @PathVariable Long userId,
			@RequestParam Integer rating, @RequestParam String comment) {

		ReviewResponseDTO review = reviewService.createReview(propertyId, userId, rating, comment);
		return ResponseEntity.status(201).body(review);
	}

	// Get all reviews for a property
	@GetMapping("/property/{propertyId}")
	public ResponseEntity<List<ReviewResponseDTO>> getReviewsByProperty(@PathVariable Long propertyId) {
		List<ReviewResponseDTO> reviews = reviewService.getReviewsByProperty(propertyId);
		return ResponseEntity.ok(reviews);
	}

	// Update a review
	@PatchMapping("/{reviewId}")
	public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long reviewId,
			@RequestParam(required = false) Integer rating, @RequestParam(required = false) String comment) {

		ReviewResponseDTO updated = reviewService.updateReview(reviewId, rating, comment);
		return ResponseEntity.ok(updated);
	}

	// Delete a review
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
		reviewService.deleteReview(reviewId);
		return ResponseEntity.noContent().build();
	}
}
