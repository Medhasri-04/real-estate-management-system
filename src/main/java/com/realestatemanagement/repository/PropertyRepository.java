package com.realestatemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	// Used when agent manages properties
	List<Property> findByAgentId(Long agentId);
}