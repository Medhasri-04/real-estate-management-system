package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.booking.BookingCreateRequest;
import com.realestatemanagement.dto.request.booking.SiteVisitRequest;
import com.realestatemanagement.dto.response.BookingResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	// 34. POST /bookings
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookingResponse create(@Valid @RequestBody BookingCreateRequest req) {
		return bookingService.create(req);
	}

	// 35. POST /bookings/site-visit
	@PostMapping("/site-visit")
	@ResponseStatus(HttpStatus.CREATED)
	public BookingResponse siteVisit(@Valid @RequestBody SiteVisitRequest req) {
		return bookingService.siteVisit(req);
	}

	// 36. GET /bookings/{bookingId}
	@GetMapping("/{bookingId}")
	public BookingResponse getOne(@PathVariable Long bookingId) {
		return bookingService.getOne(bookingId);
	}

	// 37. GET /bookings/customer
	@GetMapping("/customer")
	public List<BookingResponse> customerBookings() {
		return bookingService.customerBookings();
	}

	// 38. GET /bookings/agent
	@GetMapping("/agent")
	public List<BookingResponse> agentBookings() {
		return bookingService.agentBookings();
	}

	// 39. PUT /bookings/{bookingId}/confirm
	@PutMapping("/{bookingId}/confirm")
	public MessageResponse confirm(@PathVariable Long bookingId) {
		return bookingService.confirm(bookingId);
	}

	// 40. PUT /bookings/{bookingId}/cancel
	@PutMapping("/{bookingId}/cancel")
	public MessageResponse cancel(@PathVariable Long bookingId) {
		return bookingService.cancel(bookingId);
	}
}