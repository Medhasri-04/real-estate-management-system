package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.realestatemanagement.dto.request.BookingRequestDTO;
import com.realestatemanagement.dto.request.SiteVisitRequestDTO;
import com.realestatemanagement.dto.response.BookingResponseDTO;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.service.BookingService;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Create booking (CUSTOMER)
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO dto) {
        Long customerId = getLoggedInUserId();
     // Correct order
        BookingResponseDTO booking = bookingService.createBooking(dto, customerId);
        return ResponseEntity.status(201).body(booking);
    }

    // Create site visit (CUSTOMER)
    @PostMapping("/site-visit")
    public ResponseEntity<BookingResponseDTO> createSiteVisit(@RequestBody SiteVisitRequestDTO dto) {
        Long customerId = getLoggedInUserId();
        BookingResponseDTO booking = bookingService.createSiteVisit(dto,customerId);
        return ResponseEntity.status(201).body(booking);
    }

    // Get booking by id
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    // Get all bookings for logged-in customer
    @GetMapping("/customer")
    public ResponseEntity<List<BookingResponseDTO>> getCustomerBookings() {
        Long customerId = getLoggedInUserId();
        return ResponseEntity.ok(bookingService.getBookingsByCustomer(customerId));
    }

    // Get bookings for agent
    @GetMapping("/agent")
    public ResponseEntity<List<BookingResponseDTO>> getAgentBookings() {
        Long agentId = getLoggedInUserId();
        return ResponseEntity.ok(bookingService.getBookingsByAgent(agentId));
    }

    // Confirm booking (AGENT / ADMIN)
    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable Long bookingId) {
        Long userId = getLoggedInUserId();
        BookingResponseDTO booking = bookingService.confirmBooking(bookingId, userId);
        return ResponseEntity.ok(booking);
    }

    // Cancel booking (CUSTOMER / AGENT)
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId,
                                           @RequestParam Long customerId) {
        BookingResponseDTO dto = bookingService.cancelBooking(bookingId, customerId);
        return ResponseEntity.ok(dto);
    }


    private Long getLoggedInUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
