package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.BookingRequestDTO;
import com.realestatemanagement.dto.request.SiteVisitRequestDTO;
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

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
    }

    // 1. Create Booking
    public BookingResponseDTO createBooking(BookingRequestDTO dto, Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate() != null ? dto.getBookingDate() : LocalDateTime.now());
        booking.setStatus(dto.getStatus());
        booking.setCustomer(customer);
        booking.setProperty(property);

        booking = bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    // 2. Create Site Visit
    public BookingResponseDTO createSiteVisit(SiteVisitRequestDTO dto, Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Booking booking = new Booking();
        booking.setVisitDateTime(dto.getVisitDateTime());
        booking.setStatus(dto.getStatus());
        booking.setCustomer(customer);
        booking.setProperty(property);

        booking = bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    // 3. Get Booking by ID
    public BookingResponseDTO getBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return BookingMapper.toDto(booking);
    }

    // 4. Get bookings by customer
    public List<BookingResponseDTO> getBookingsByCustomer(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return bookingRepository.findByCustomer(customer)
                .stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // 5. Get bookings by agent (based on properties they manage)
    public List<BookingResponseDTO> getBookingsByAgent(Long agentId) {
        User agent = userRepository.findById(agentId)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return bookingRepository.findByPropertyAgent(agent)
                .stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }

    // 6. Confirm Booking
    public BookingResponseDTO confirmBooking(Long bookingId, Long agentId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CONFIRMED");
        booking = bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }

    // 7. Cancel Booking
    public BookingResponseDTO cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        booking = bookingRepository.save(booking);
        return BookingMapper.toDto(booking);
    }
}
