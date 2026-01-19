package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.property.AssignAgentRequest;
import com.realestatemanagement.dto.request.property.LinkAmenitiesRequest;
import com.realestatemanagement.dto.request.property.PropertyCreateRequest;
import com.realestatemanagement.dto.request.property.PropertyUpdateRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.PropertyNearbyResponse;
import com.realestatemanagement.dto.response.PropertyResponse;
import com.realestatemanagement.entity.Amenity;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.PropertyMapper;
import com.realestatemanagement.repository.AmenityRepository;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class PropertyService {
	private final PropertyRepository propertyRepo;
	private final UserRepository userRepo;
	private final AmenityRepository amenityRepo;
	private final PropertyMapper mapper;

	public PropertyService(PropertyRepository propertyRepo, UserRepository userRepo, AmenityRepository amenityRepo,
			PropertyMapper mapper) {
		this.propertyRepo = propertyRepo;
		this.userRepo = userRepo;
		this.amenityRepo = amenityRepo;
		this.mapper = mapper;
	}

	// Helper: current logged-in user (email is stored in JWT subject)
	private User currentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = principal.toString();
		return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	}

	// 21) POST /properties (AGENT)
	public PropertyResponse create(PropertyCreateRequest req) {
		User agent = currentUser();
		Property p = new Property();
		p.setTitle(req.getTitle());
		p.setDescription(req.getDescription());
		p.setPrice(req.getPrice());
		p.setLocation(req.getLocation());
		p.setPropertyType(req.getPropertyType());
		p.setAvailability(req.getAvailability());
		p.setPropertyAge(req.getPropertyAge());
		p.setFurnishingStatus(req.getFurnishingStatus());
		p.setLatitude(req.getLatitude());
		p.setLongitude(req.getLongitude());
		p.setAgent(agent);
		p = propertyRepo.save(p);
		return mapper.toResponse(p);
	}

	// 22) GET /properties
	public List<PropertyResponse> getAll() {
		List<PropertyResponse> out = new ArrayList<>();
		for (Property p : propertyRepo.findAll()) {
			out.add(mapper.toResponse(p));
		}
		return out;
	}

	// 23) GET /properties/{propertyId}
	public PropertyResponse getById(Long propertyId) {
		Property p = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		return mapper.toResponse(p);
	}

	// 24) GET /properties/nearby?lat=&lng=&radiusKm=
	public List<PropertyNearbyResponse> nearby(double lat, double lng, double radiusKm) {
		List<PropertyNearbyResponse> out = new ArrayList<>();
		for (Property p : propertyRepo.findAll()) {
			if (p.getLatitude() == null || p.getLongitude() == null)
				continue;
			double d = haversineKm(lat, lng, p.getLatitude(), p.getLongitude());
			if (d <= radiusKm) {
				PropertyNearbyResponse r = new PropertyNearbyResponse();
				r.setPropertyId(p.getId());
				r.setTitle(p.getTitle());
				r.setDistanceKm(Math.round(d * 10.0) / 10.0);
				out.add(r);
			}
		}
		return out;
	}

	// 25) PUT /properties/{propertyId}
	public MessageResponse update(Long propertyId, PropertyUpdateRequest req) {
		Property p = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		// update only fields you allow in your API
		if (req.getPrice() != null)
			p.setPrice(req.getPrice());
		if (req.getAvailability() != null)
			p.setAvailability(req.getAvailability());
		if (req.getFurnishingStatus() != null)
			p.setFurnishingStatus(req.getFurnishingStatus());
		propertyRepo.save(p);
		return new MessageResponse("Property updated", LocalDateTime.now());
	}

	// 26) DELETE /properties/{propertyId}
	public void delete(Long propertyId) {
		if (!propertyRepo.existsById(propertyId)) {
			throw new RuntimeException("Property not found");
		}
		propertyRepo.deleteById(propertyId);
	}

	// 27) POST /properties/{propertyId}/assign-agent (ADMIN)
	public MessageResponse assignAgent(Long propertyId, AssignAgentRequest req) {
		Property p = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		User agent = userRepo.findById(req.getAgentId()).orElseThrow(() -> new RuntimeException("Agent not found"));
		p.setAgent(agent);
		propertyRepo.save(p);
		return new MessageResponse("Agent assigned", LocalDateTime.now());
	}

	// 28) GET /properties/{propertyId}/amenities
	public List<String> getAmenities(Long propertyId) {
		Property p = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		List<String> names = new ArrayList<>();
		if (p.getAmenities() != null) {
			for (Amenity a : p.getAmenities()) {
				names.add(a.getName());
			}
		}
		return names;
	}

	// 29) POST /properties/{propertyId}/amenities
	public MessageResponse addAmenities(Long propertyId, LinkAmenitiesRequest req) {
		Property p = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		if (req.getAmenityIds() == null || req.getAmenityIds().isEmpty()) {
			throw new RuntimeException("amenityIds is required");
		}
		if (p.getAmenities() == null) {
			p.setAmenities(new ArrayList<>());
		}
		for (Long amenityId : req.getAmenityIds()) {
			Amenity a = amenityRepo.findById(amenityId)
					.orElseThrow(() -> new RuntimeException("Amenity not found: " + amenityId));
			boolean exists = false;
			for (Amenity existing : p.getAmenities()) {
				if (existing.getId().equals(a.getId())) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				p.getAmenities().add(a);
			}
		}
		propertyRepo.save(p);
		return new MessageResponse("Amenities linked to property", LocalDateTime.now());
	}

	// 30) DELETE /properties/{propertyId}/amenities/{amenityId}
	public void removeAmenity(Long propertyId, Long amenityId) {
		Property p = propertyRepo.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
		if (p.getAmenities() == null)
			return;
		p.getAmenities().removeIf(a -> a.getId().equals(amenityId));
		propertyRepo.save(p);
	}

	// Basic distance function (Haversine)
	private double haversineKm(double lat1, double lon1, double lat2, double lon2) {
		double R = 6371.0;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}
}