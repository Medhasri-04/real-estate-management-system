package com.realestatemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	// GET /properties/{propertyId}/reviews
	List<Review> findByPropertyId(Long propertyId);
}