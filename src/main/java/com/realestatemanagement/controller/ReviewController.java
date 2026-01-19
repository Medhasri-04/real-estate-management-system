package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.review.ReviewCreateRequest;
import com.realestatemanagement.dto.request.review.ReviewUpdateRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.ReviewResponse;
import com.realestatemanagement.dto.response.ReviewSummaryResponse;
import com.realestatemanagement.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	// 41. POST /properties/{propertyId}/reviews (CUSTOMER)
	@PostMapping("/properties/{propertyId}/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse add(@PathVariable Long propertyId, @Valid @RequestBody ReviewCreateRequest req) {
		return reviewService.add(propertyId, req);
	}

	// 42. GET /properties/{propertyId}/reviews
	@GetMapping("/properties/{propertyId}/reviews")
	public List<ReviewResponse> list(@PathVariable Long propertyId) {
		return reviewService.list(propertyId);
	}

	// 43. GET /properties/{propertyId}/reviews/summary
	@GetMapping("/properties/{propertyId}/reviews/summary")
	public ReviewSummaryResponse summary(@PathVariable Long propertyId) {
		return reviewService.summary(propertyId);
	}

	// 44. PATCH /reviews/{reviewId}
	@PatchMapping("/reviews/{reviewId}")
	public MessageResponse update(@PathVariable Long reviewId, @Valid @RequestBody ReviewUpdateRequest req) {
		return reviewService.update(reviewId, req);
	}

	// 44. DELETE /reviews/{reviewId}
	@DeleteMapping("/reviews/{reviewId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long reviewId) {
		reviewService.delete(reviewId);
	}
}