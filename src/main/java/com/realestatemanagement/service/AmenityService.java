package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.amenity.AmenityRequest;
import com.realestatemanagement.dto.response.AmenityResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.entity.Amenity;
import com.realestatemanagement.mapper.AmenityMapper;
import com.realestatemanagement.repository.AmenityRepository;

@Service
public class AmenityService {
	private final AmenityRepository amenityRepository;
	private final AmenityMapper amenityMapper;

	public AmenityService(AmenityRepository amenityRepository, AmenityMapper amenityMapper) {
		this.amenityRepository = amenityRepository;
		this.amenityMapper = amenityMapper;
	}

	// POST /amenities (ADMIN)
	public AmenityResponse create(AmenityRequest req) {
		if (amenityRepository.existsByName(req.getName())) {
			throw new RuntimeException("Amenity already exists");
		}
		Amenity a = new Amenity();
		a.setName(req.getName());
		a = amenityRepository.save(a);
		return amenityMapper.toResponse(a);
	}

	// GET /amenities
	public List<AmenityResponse> getAll() {
		List<AmenityResponse> res = new ArrayList<>();
		for (Amenity a : amenityRepository.findAll()) {
			res.add(amenityMapper.toResponse(a));
		}
		return res;
	}

	// GET /amenities/{amenityId}
	public AmenityResponse getOne(Long amenityId) {
		Amenity a = amenityRepository.findById(amenityId).orElseThrow(() -> new RuntimeException("Amenity not found"));
		return amenityMapper.toResponse(a);
	}

	// PUT /amenities/{amenityId}
	public MessageResponse update(Long amenityId, AmenityRequest req) {
		Amenity a = amenityRepository.findById(amenityId).orElseThrow(() -> new RuntimeException("Amenity not found"));
		// if renaming, prevent duplicates
		if (!a.getName().equalsIgnoreCase(req.getName()) && amenityRepository.existsByName(req.getName())) {
			throw new RuntimeException("Amenity name already exists");
		}
		a.setName(req.getName());
		amenityRepository.save(a);
		return new MessageResponse("Amenity updated", LocalDateTime.now());
	}

	// DELETE /amenities/{amenityId}
	public void delete(Long amenityId) {
		if (!amenityRepository.existsById(amenityId)) {
			throw new RuntimeException("Amenity not found");
		}
		amenityRepository.deleteById(amenityId);
	}
}