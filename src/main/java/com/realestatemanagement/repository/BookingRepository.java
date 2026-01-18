package com.realestatemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestatemanagement.entity.Booking;
import com.realestatemanagement.entity.User;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	// Find all bookings for a given customer
	List<Booking> findByCustomer(User customer);

	// Find all bookings for properties assigned to an agent
	List<Booking> findByPropertyAgent(User agent);
}
