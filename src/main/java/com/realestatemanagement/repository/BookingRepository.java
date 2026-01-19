package com.realestatemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	// GET /bookings/customer
	List<Booking> findByCustomerId(Long customerId);

	// GET /bookings/agent
	List<Booking> findByPropertyAgentId(Long agentId);

	// optional helper
	List<Booking> findByPropertyId(Long propertyId);
}