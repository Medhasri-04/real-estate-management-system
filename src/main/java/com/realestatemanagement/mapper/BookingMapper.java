package com.realestatemanagement.mapper;

import org.springframework.stereotype.Component;

import com.realestatemanagement.dto.response.BookingResponse;
import com.realestatemanagement.entity.Booking;

@Component
public class BookingMapper {
	public BookingResponse toResponse(Booking b) {
		BookingResponse r = new BookingResponse();
		r.setBookingId(b.getId());
		r.setPropertyId(b.getProperty() != null ? b.getProperty().getId() : null);
		r.setCustomerId(b.getCustomer() != null ? b.getCustomer().getId() : null);
		r.setStatus(b.getStatus());
		r.setType(b.getType());
		r.setBookingDate(b.getBookingDate());
		r.setVisitDateTime(b.getVisitDateTime());
		r.setCreatedAt(b.getCreatedAt());
		r.setUpdatedAt(b.getUpdatedAt());
		return r;
	}
}