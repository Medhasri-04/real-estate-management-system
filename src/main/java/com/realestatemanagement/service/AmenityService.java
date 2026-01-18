package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.AmenityRequestDTO;
import com.realestatemanagement.dto.response.AmenityResponseDTO;
import com.realestatemanagement.entity.Amenity;
import com.realestatemanagement.mapper.AmenityMapper;
import com.realestatemanagement.repository.AmenityRepository;

@Service
public class AmenityService {

	private final AmenityRepository amenityRepository;

	public AmenityService(AmenityRepository amenityRepository) {
		this.amenityRepository = amenityRepository;
	}

	// CREATE
	public AmenityResponseDTO createAmenity(AmenityRequestDTO dto) {
		if (amenityRepository.existsByName(dto.getName())) {
			throw new RuntimeException("Amenity already exists");
		}

		Amenity amenity = AmenityMapper.toEntity(dto);
		amenity = amenityRepository.save(amenity);
		return AmenityMapper.toDto(amenity);
	}

	// GET ALL
	public List<AmenityResponseDTO> getAllAmenities() {
		return amenityRepository.findAll().stream().map(AmenityMapper::toDto).collect(Collectors.toList());
	}

	// GET BY ID
	public AmenityResponseDTO getAmenityById(Long id) {
		Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new RuntimeException("Amenity not found"));
		return AmenityMapper.toDto(amenity);
	}

	// UPDATE
	public AmenityResponseDTO updateAmenity(Long id, AmenityRequestDTO dto) {
		Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new RuntimeException("Amenity not found"));

		amenity.setName(dto.getName());
		amenity = amenityRepository.save(amenity);
		return AmenityMapper.toDto(amenity);
	}

	// DELETE
	public void deleteAmenity(Long id) {
		Amenity amenity = amenityRepository.findById(id).orElseThrow(() -> new RuntimeException("Amenity not found"));
		amenityRepository.delete(amenity);
	}
}
