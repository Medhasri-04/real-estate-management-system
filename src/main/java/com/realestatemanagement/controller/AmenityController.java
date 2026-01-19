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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.amenity.AmenityRequest;
import com.realestatemanagement.dto.response.AmenityResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.service.AmenityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/amenities")
public class AmenityController {
	private final AmenityService amenityService;

	public AmenityController(AmenityService amenityService) {
		this.amenityService = amenityService;
	}

	// 16. POST /amenities (ADMIN)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AmenityResponse create(@Valid @RequestBody AmenityRequest req) {
		return amenityService.create(req);
	}

	// 17. GET /amenities
	@GetMapping
	public List<AmenityResponse> getAll() {
		return amenityService.getAll();
	}

	// 18. GET /amenities/{amenityId}
	@GetMapping("/{amenityId}")
	public AmenityResponse getOne(@PathVariable Long amenityId) {
		return amenityService.getOne(amenityId);
	}

	// 19. PUT /amenities/{amenityId}
	@PutMapping("/{amenityId}")
	public MessageResponse update(@PathVariable Long amenityId, @Valid @RequestBody AmenityRequest req) {
		return amenityService.update(amenityId, req);
	}

	// 20. DELETE /amenities/{amenityId}
	@DeleteMapping("/{amenityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long amenityId) {
		amenityService.delete(amenityId);
	}
}