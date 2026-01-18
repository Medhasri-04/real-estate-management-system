package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.response.ReviewResponseDTO;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.Review;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.ReviewRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final PropertyRepository propertyRepository;
	private final UserRepository userRepository;

	public ReviewService(ReviewRepository reviewRepository, PropertyRepository propertyRepository,
			UserRepository userRepository) {

		this.reviewRepository = reviewRepository;
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;
	}

	// CREATE REVIEW
	public ReviewResponseDTO createReview(Long propertyId, Long customerId, Integer rating, String comment) {

		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));

		User customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("User not found"));

		Review review = new Review();
		review.setProperty(property);
		review.setCustomer(customer);
		review.setRating(rating);
		review.setComment(comment);

		// ❌ DO NOT set createdAt manually

		Review savedReview = reviewRepository.save(review);
		return mapToDTO(savedReview);
	}

	// GET REVIEWS BY PROPERTY
	public List<ReviewResponseDTO> getReviewsByProperty(Long propertyId) {

		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));

		return reviewRepository.findByProperty(property).stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// UPDATE REVIEW
	public ReviewResponseDTO updateReview(Long reviewId, Integer rating, String comment) {

		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));

		if (rating != null) {
			review.setRating(rating);
		}
		if (comment != null) {
			review.setComment(comment);
		}

		// ❌ DO NOT set updatedAt manually

		Review updatedReview = reviewRepository.save(review);
		return mapToDTO(updatedReview);
	}

	// DELETE REVIEW
	public void deleteReview(Long reviewId) {

		Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));

		reviewRepository.delete(review);
	}

	// ENTITY → DTO
	private ReviewResponseDTO mapToDTO(Review review) {

		ReviewResponseDTO dto = new ReviewResponseDTO();
		dto.setId(review.getId());
		dto.setPropertyId(review.getProperty().getId());
		dto.setPropertyTitle(review.getProperty().getTitle());
		dto.setUserId(review.getCustomer().getId());
		dto.setUserName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName());
		dto.setRating(review.getRating());
		dto.setComment(review.getComment());
		dto.setCreatedAt(review.getCreatedAt()); // from BaseEntity

		return dto;
	}
}
