package com.realestatemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
	Optional<Amenity> findByName(String name);

	boolean existsByName(String name);
}