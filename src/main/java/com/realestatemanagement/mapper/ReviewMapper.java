package com.realestatemanagement.mapper;

import com.realestatemanagement.dto.request.ReviewRequestDTO;
import com.realestatemanagement.dto.response.ReviewResponseDTO;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.Review;
import com.realestatemanagement.entity.User;

public class ReviewMapper {

    public static ReviewResponseDTO toDto(Review review) {
        if (review == null) return null;

        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        if (review.getCustomer() != null) {
            dto.setUserId(review.getCustomer().getId());
            dto.setUserName(
                review.getCustomer().getFirstName() + " " +
                review.getCustomer().getLastName()
            );
        }

        if (review.getProperty() != null) {
            dto.setPropertyId(review.getProperty().getId());
            dto.setPropertyTitle(review.getProperty().getTitle());
        }

        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }

    public static Review toEntity(ReviewRequestDTO dto, User customer, Property property) {
        if (dto == null) return null;

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setCustomer(customer);
        review.setProperty(property);
        return review;
    }
}
