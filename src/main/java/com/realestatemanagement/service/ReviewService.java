package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.review.ReviewCreateRequest;
import com.realestatemanagement.dto.request.review.ReviewUpdateRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.ReviewResponse;
import com.realestatemanagement.dto.response.ReviewSummaryResponse;
import com.realestatemanagement.entity.Review;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.ReviewMapper;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.ReviewRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepo;
	private final PropertyRepository propertyRepo;
	private final UserRepository userRepo;
	private final ReviewMapper mapper;

	public ReviewService(ReviewRepository reviewRepo, PropertyRepository propertyRepo, UserRepository userRepo,
			ReviewMapper mapper) {
		this.reviewRepo = reviewRepo;
		this.propertyRepo = propertyRepo;
		this.userRepo = userRepo;
		this.mapper = mapper;
	}

	private User currentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

	// 41) POST /properties/{propertyId}/reviews
	public MessageResponse add(Long propertyId, ReviewCreateRequest req) {
		Review r = new Review();
		r.setRating(req.getRating());
		r.setComment(req.getComment());
		r.setProperty(propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found")));
		r.setCustomer(currentUser());
		reviewRepo.save(r);
		return new MessageResponse("Review submitted", LocalDateTime.now());
	}

	// 42) GET /properties/{propertyId}/reviews
	public List<ReviewResponse> list(Long propertyId) {
		List<ReviewResponse> res = new ArrayList<>();
		for (Review r : reviewRepo.findByPropertyId(propertyId)) {
			res.add(mapper.toResponse(r));
		}
		return res;
	}

	// 43) GET /properties/{propertyId}/reviews/summary
	public ReviewSummaryResponse summary(Long propertyId) {
		List<Review> reviews = reviewRepo.findByPropertyId(propertyId);
		double sum = 0;
		Map<String, Integer> breakdown = new HashMap<>();
		breakdown.put("1", 0);
		breakdown.put("2", 0);
		breakdown.put("3", 0);
		breakdown.put("4", 0);
		breakdown.put("5", 0);
		for (Review r : reviews) {
			sum += r.getRating();
			String key = String.valueOf(r.getRating());
			breakdown.put(key, breakdown.get(key) + 1);
		}
		ReviewSummaryResponse resp = new ReviewSummaryResponse();
		resp.setPropertyId(propertyId);
		resp.setTotalReviews(reviews.size());
		resp.setAverageRating(reviews.isEmpty() ? 0 : sum / reviews.size());
		resp.setBreakdown(breakdown);
		return resp;
	}

	// 44) PATCH /reviews/{reviewId}
	public MessageResponse update(Long reviewId, ReviewUpdateRequest req) {
		Review r = reviewRepo.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));
		r.setRating(req.getRating());
		r.setComment(req.getComment());
		reviewRepo.save(r);
		return new MessageResponse("Review updated", LocalDateTime.now());
	}

	// 44) DELETE /reviews/{reviewId}
	public void delete(Long reviewId) {
		if (!reviewRepo.existsById(reviewId)) {
			throw new RuntimeException("Review not found");
		}
		reviewRepo.deleteById(reviewId);
	}
}