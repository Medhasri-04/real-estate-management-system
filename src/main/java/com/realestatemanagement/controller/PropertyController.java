package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.property.AssignAgentRequest;
import com.realestatemanagement.dto.request.property.LinkAmenitiesRequest;
import com.realestatemanagement.dto.request.property.PropertyCreateRequest;
import com.realestatemanagement.dto.request.property.PropertyUpdateRequest;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.PropertyNearbyResponse;
import com.realestatemanagement.dto.response.PropertyResponse;
import com.realestatemanagement.service.PropertyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/properties")
public class PropertyController {
	private final PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	// 21. POST /properties (AGENT)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PropertyResponse create(@Valid @RequestBody PropertyCreateRequest req) {
		return propertyService.create(req);
	}

	// 22. GET /properties
	@GetMapping
	public List<PropertyResponse> getAll() {
		return propertyService.getAll();
	}

	// 23. GET /properties/{propertyId}
	@GetMapping("/{propertyId}")
	public PropertyResponse getById(@PathVariable Long propertyId) {
		return propertyService.getById(propertyId);
	}

	// 24. GET /properties/nearby?lat=..&lng=..&radiusKm=..
	@GetMapping("/nearby")
	public List<PropertyNearbyResponse> nearby(@RequestParam double lat, @RequestParam double lng,
			@RequestParam double radiusKm) {
		return propertyService.nearby(lat, lng, radiusKm);
	}

	// 25. PUT /properties/{propertyId}
	@PutMapping("/{propertyId}")
	public MessageResponse update(@PathVariable Long propertyId, @Valid @RequestBody PropertyUpdateRequest req) {
		return propertyService.update(propertyId, req);
	}

	// 26. DELETE /properties/{propertyId}
	@DeleteMapping("/{propertyId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long propertyId) {
		propertyService.delete(propertyId);
	}

	// 27. POST /properties/{propertyId}/assign-agent (ADMIN)
	@PostMapping("/{propertyId}/assign-agent")
	public MessageResponse assignAgent(@PathVariable Long propertyId, @Valid @RequestBody AssignAgentRequest req) {
		return propertyService.assignAgent(propertyId, req);
	}

	// 28. GET /properties/{propertyId}/amenities
	@GetMapping("/{propertyId}/amenities")
	public List<String> getAmenities(@PathVariable Long propertyId) {
		return propertyService.getAmenities(propertyId);
	}

	// 29. POST /properties/{propertyId}/amenities
	@PostMapping("/{propertyId}/amenities")
	public MessageResponse addAmenities(@PathVariable Long propertyId, @Valid @RequestBody LinkAmenitiesRequest req) {
		return propertyService.addAmenities(propertyId, req);
	}

	// 30. DELETE /properties/{propertyId}/amenities/{amenityId}
	@DeleteMapping("/{propertyId}/amenities/{amenityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeAmenity(@PathVariable Long propertyId, @PathVariable Long amenityId) {
		propertyService.removeAmenity(propertyId, amenityId);
	}
}