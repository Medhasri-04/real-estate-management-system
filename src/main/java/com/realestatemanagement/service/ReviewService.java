package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.ReviewRequestDTO;
import com.realestatemanagement.dto.response.ReviewResponseDTO;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.Review;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.ReviewMapper;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.ReviewRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final PropertyRepository propertyRepository;

	public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
			PropertyRepository propertyRepository) {
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
	}

	public ReviewResponseDTO createReview(ReviewRequestDTO dto) {
		User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
		Property property = propertyRepository.findById(dto.getPropertyId())
				.orElseThrow(() -> new RuntimeException("Property not found"));

		Review review = ReviewMapper.toEntity(dto, user, property);
		review = reviewRepository.save(review);

		return ReviewMapper.toDto(review);
	}

	public List<ReviewResponseDTO> getAllReviews() {
		return reviewRepository.findAll().stream().map(ReviewMapper::toDto).collect(Collectors.toList());
	}
}
