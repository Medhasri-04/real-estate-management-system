package com.realestatemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	// GET /me/favorites
	List<Favorite> findByUserId(Long userId);

	// prevent duplicates + remove
	boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);

	Optional<Favorite> findByUserIdAndPropertyId(Long userId, Long propertyId);

	void deleteByUserIdAndPropertyId(Long userId, Long propertyId);
}