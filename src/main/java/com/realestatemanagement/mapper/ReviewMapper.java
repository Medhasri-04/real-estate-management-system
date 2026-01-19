package com.realestatemanagement.mapper;

import org.springframework.stereotype.Component;

import com.realestatemanagement.dto.response.ReviewResponse;
import com.realestatemanagement.entity.Review;

@Component
public class ReviewMapper {
	public ReviewResponse toResponse(Review rEntity) {
		ReviewResponse r = new ReviewResponse();
		r.setReviewId(rEntity.getId());
		r.setRating(rEntity.getRating());
		r.setComment(rEntity.getComment());
		r.setCreatedAt(rEntity.getCreatedAt());
		return r;
	}
}