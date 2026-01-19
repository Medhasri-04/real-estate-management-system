package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.booking.BookingCreateRequest;
import com.realestatemanagement.dto.request.booking.SiteVisitRequest;
import com.realestatemanagement.dto.response.BookingResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.entity.Booking;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.BookingMapper;
import com.realestatemanagement.repository.BookingRepository;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class BookingService {
	private final BookingRepository bookingRepo;
	private final PropertyRepository propertyRepo;
	private final UserRepository userRepo;
	private final BookingMapper mapper;

	public BookingService(BookingRepository bookingRepo, PropertyRepository propertyRepo, UserRepository userRepo,
			BookingMapper mapper) {
		this.bookingRepo = bookingRepo;
		this.propertyRepo = propertyRepo;
		this.userRepo = userRepo;
		this.mapper = mapper;
	}

	private User currentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).orElseThrow();
	}

	// 34) POST /bookings
	public BookingResponse create(BookingCreateRequest req) {
		Property property = propertyRepo.findById(req.getPropertyId()).orElseThrow();
		Booking b = new Booking();
		b.setProperty(property);
		b.setCustomer(currentUser());
		b.setBookingDate(req.getBookingDate());
		b.setStatus(req.getStatus());
		bookingRepo.save(b);
		return mapper.toResponse(b);
	}

	// 35) POST /bookings/site-visit
	public BookingResponse siteVisit(SiteVisitRequest req) {
		Property property = propertyRepo.findById(req.getPropertyId()).orElseThrow();
		Booking b = new Booking();
		b.setProperty(property);
		b.setCustomer(currentUser());
		b.setVisitDateTime(req.getVisitDateTime());
		b.setStatus(req.getStatus());
		bookingRepo.save(b);
		return mapper.toResponse(b);
	}

	// 36) GET /bookings/{bookingId}
	public BookingResponse getOne(Long bookingId) {
		return mapper.toResponse(bookingRepo.findById(bookingId).orElseThrow());
	}

	// 37) GET /bookings/customer
	public List<BookingResponse> customerBookings() {
		List<BookingResponse> list = new ArrayList<>();
		for (Booking b : bookingRepo.findByCustomerId(currentUser().getId())) {
			list.add(mapper.toResponse(b));
		}
		return list;
	}

	// 38) GET /bookings/agent
	public List<BookingResponse> agentBookings() {
		List<BookingResponse> list = new ArrayList<>();
		for (Booking b : bookingRepo.findByPropertyAgentId(currentUser().getId())) {
			list.add(mapper.toResponse(b));
		}
		return list;
	}

	// 39) PUT /bookings/{bookingId}/confirm
	public MessageResponse confirm(Long bookingId) {
		Booking b = bookingRepo.findById(bookingId).orElseThrow();
		b.setStatus("CONFIRMED");
		bookingRepo.save(b);
		return new MessageResponse("Booking confirmed", LocalDateTime.now());
	}

	// 40) PUT /bookings/{bookingId}/cancel
	public MessageResponse cancel(Long bookingId) {
		Booking b = bookingRepo.findById(bookingId).orElseThrow();
		b.setStatus("CANCELLED");
		bookingRepo.save(b);
		return new MessageResponse("Booking cancelled", LocalDateTime.now());
	}
}