package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Amenity;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

	Optional<Amenity> findByName(String name);
}
