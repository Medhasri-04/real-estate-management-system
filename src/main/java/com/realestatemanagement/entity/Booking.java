package com.realestatemanagement.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime bookingDate;
	private OffsetDateTime visitDateTime;
	// "PENDING", "CONFIRMED", "CANCELLED"
	private String status = "PENDING";
	private String type;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private User customer;
	@ManyToOne
	@JoinColumn(name = "property_id")
	private Property property;

	public Booking() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public OffsetDateTime getVisitDateTime() {
		return visitDateTime;
	}

	public void setVisitDateTime(OffsetDateTime visitDateTime) {
		this.visitDateTime = visitDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
}