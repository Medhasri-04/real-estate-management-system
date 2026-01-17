package com.realestatemanagement.mapper;

import java.time.LocalDateTime;

import com.realestatemanagement.dto.request.BookingRequestDTO;
import com.realestatemanagement.dto.response.BookingResponseDTO;
import com.realestatemanagement.entity.Booking;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;

public class BookingMapper {

    public static BookingResponseDTO toDto(Booking booking) {
        if (booking == null) return null;

        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setBookingDate(booking.getBookingDate());
        dto.setVisitDateTime(booking.getVisitDateTime());
        dto.setStatus(booking.getStatus());

        if (booking.getCustomer() != null) {
            dto.setCustomerId(booking.getCustomer().getId());
            dto.setCustomerName(
                booking.getCustomer().getFirstName() + " " +
                booking.getCustomer().getLastName()
            );
        }

        if (booking.getProperty() != null) {
            dto.setPropertyId(booking.getProperty().getId());
            dto.setPropertyTitle(booking.getProperty().getTitle());
        }

        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        return dto;
    }

    public static Booking toEntity(BookingRequestDTO dto, User customer, Property property) {
        if (dto == null) return null;

        Booking booking = new Booking();
        booking.setBookingDate(
            dto.getBookingDate() != null ? dto.getBookingDate() : LocalDateTime.now()
        );
        booking.setVisitDateTime(dto.getVisitDateTime());
        booking.setStatus(dto.getStatus());
        booking.setCustomer(customer);
        booking.setProperty(property);
        return booking;
    }
}
