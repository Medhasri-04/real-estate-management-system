package com.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Property extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(length = 2000)
	private String description;

	private Double price;
	private String location;

	// RENT / SALE
	private String propertyType;

	private Boolean availability;
	private Integer propertyAge;
	private String furnishingStatus;

	private Double latitude;
	private Double longitude;

	@ManyToOne
	@JoinColumn(name = "agent_id")
	private User agent;

	@OneToMany(mappedBy = "property")
	private List<Booking> bookings;

	@OneToMany(mappedBy = "property")
	private List<Review> reviews;

	@ManyToMany
	@JoinTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"))
	private Set<Amenity> amenities;

	public Property() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
}
