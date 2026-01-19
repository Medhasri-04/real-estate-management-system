package com.realestatemanagement.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.availability.AvailabilityBlockRequest;
import com.realestatemanagement.dto.response.AvailabilityResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.service.AvailabilityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/properties/{propertyId}/availability")
public class AvailabilityController {
	private final AvailabilityService availabilityService;

	public AvailabilityController(AvailabilityService availabilityService) {
		this.availabilityService = availabilityService;
	}

	// 31. GET /properties/{propertyId}/availability?from=...&to=...
	@GetMapping
	public AvailabilityResponse getAvailability(@PathVariable Long propertyId,
			@RequestParam(required = false) String from, @RequestParam(required = false) String to) {
		// beginner: from/to ignored in service for now
		return availabilityService.getAvailability(propertyId);
	}

	// 32. PUT /properties/{propertyId}/availability/block
	@PutMapping("/block")
	public MessageResponse block(@PathVariable Long propertyId, @Valid @RequestBody AvailabilityBlockRequest req) {
		return availabilityService.blockAvailability(propertyId, req);
	}

	// 33. DELETE /properties/{propertyId}/availability/block/{blockId}
	@DeleteMapping("/block/{blockId}")
	public MessageResponse removeBlock(@PathVariable Long propertyId, @PathVariable Long blockId) {
		return availabilityService.removeBlock(propertyId, blockId);
	}
}