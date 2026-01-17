package com.realestatemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestatemanagement.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
