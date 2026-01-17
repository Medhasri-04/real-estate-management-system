package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
