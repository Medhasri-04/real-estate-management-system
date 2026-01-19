package com.realestatemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.AvailabilityBlock;

public interface AvailabilityBlockRepository extends JpaRepository<AvailabilityBlock, Long> {
	List<AvailabilityBlock> findByPropertyId(Long propertyId);
}