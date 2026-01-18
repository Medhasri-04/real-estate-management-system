package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.request.AmenityRequestDTO;
import com.realestatemanagement.dto.response.AmenityResponseDTO;
import com.realestatemanagement.service.AmenityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/amenities")
public class AmenityController {

	private final AmenityService amenityService;

	public AmenityController(AmenityService amenityService) {
		this.amenityService = amenityService;
	}

	// CREATE (ADMIN)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AmenityResponseDTO> createAmenity(@Valid @RequestBody AmenityRequestDTO dto) {

		AmenityResponseDTO response = amenityService.createAmenity(dto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// GET ALL (PUBLIC)
	@GetMapping
	public ResponseEntity<List<AmenityResponseDTO>> getAllAmenities() {
		return ResponseEntity.ok(amenityService.getAllAmenities());
	}

	// GET BY ID
	@GetMapping("/{id}")
	public ResponseEntity<AmenityResponseDTO> getAmenityById(@PathVariable Long id) {
		return ResponseEntity.ok(amenityService.getAmenityById(id));
	}

	// UPDATE (ADMIN)
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<AmenityResponseDTO> updateAmenity(@PathVariable Long id,
			@Valid @RequestBody AmenityRequestDTO dto) {

		return ResponseEntity.ok(amenityService.updateAmenity(id, dto));
	}

	// DELETE (ADMIN)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
		amenityService.deleteAmenity(id);
		return ResponseEntity.noContent().build();
	}
}
