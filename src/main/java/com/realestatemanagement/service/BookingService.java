package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.BookingRequestDTO;
import com.realestatemanagement.dto.response.BookingResponseDTO;
import com.realestatemanagement.entity.Booking;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.BookingMapper;
import com.realestatemanagement.repository.BookingRepository;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class BookingService {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final PropertyRepository propertyRepository;

	public BookingService(BookingRepository bookingRepository, UserRepository userRepository,
			PropertyRepository propertyRepository) {
		this.bookingRepository = bookingRepository;
		this.userRepository = userRepository;
		this.propertyRepository = propertyRepository;
	}

	// Create booking
	public BookingResponseDTO createBooking(BookingRequestDTO dto) {
		User customer = userRepository.findById(dto.getCustomerId())
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		Property property = propertyRepository.findById(dto.getPropertyId())
				.orElseThrow(() -> new RuntimeException("Property not found"));

		Booking booking = BookingMapper.toEntity(dto, customer, property);
		booking = bookingRepository.save(booking);
		return BookingMapper.toDto(booking);
	}

	// Get booking by ID
	public BookingResponseDTO getBookingById(Long id) {
		return bookingRepository.findById(id).map(BookingMapper::toDto).orElse(null);
	}

	// Get all bookings
	public List<BookingResponseDTO> getAllBookings() {
		return bookingRepository.findAll().stream().map(BookingMapper::toDto).collect(Collectors.toList());
	}
}
