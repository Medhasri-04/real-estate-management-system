package com.realestatemanagement.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.PropertyRequestDTO;
import com.realestatemanagement.dto.response.PropertyResponseDTO;
import com.realestatemanagement.entity.Amenity;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.PropertyMapper;
import com.realestatemanagement.repository.AmenityRepository;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class PropertyService {

	private final PropertyRepository propertyRepository;
	private final UserRepository userRepository;
	private final AmenityRepository amenityRepository;

	public PropertyService(PropertyRepository propertyRepository, UserRepository userRepository,
			AmenityRepository amenityRepository) {
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;
		this.amenityRepository = amenityRepository;
	}

	// CREATE PROPERTY (AGENT)
	public PropertyResponseDTO createProperty(PropertyRequestDTO dto, Long agentId) {

		User agent = userRepository.findById(agentId).orElseThrow(() -> new RuntimeException("Agent not found"));

		Property property = new Property();
		property.setTitle(dto.getTitle());
		property.setDescription(dto.getDescription());
		property.setPrice(dto.getPrice());
		property.setLocation(dto.getLocation());
		property.setPropertyType(dto.getPropertyType()); // String
		property.setAvailability(dto.getAvailability());
		property.setPropertyAge(dto.getPropertyAge());
		property.setFurnishingStatus(dto.getFurnishingStatus());
		property.setLatitude(dto.getLatitude());
		property.setLongitude(dto.getLongitude());
		property.setAgent(agent);

		return PropertyMapper.toDto(propertyRepository.save(property));
	}

	// GET ALL PROPERTIES
	public List<PropertyResponseDTO> getAllProperties() {
		return propertyRepository.findAll().stream().map(PropertyMapper::toDto).collect(Collectors.toList());
	}

	// GET PROPERTY BY ID
	public PropertyResponseDTO getPropertyById(Long propertyId) {
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));
		return PropertyMapper.toDto(property);
	}

	// UPDATE PROPERTY
	public PropertyResponseDTO updateProperty(Long propertyId, PropertyRequestDTO dto) {

		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));

		if (dto.getPrice() != null)
			property.setPrice(dto.getPrice());
		if (dto.getAvailability() != null)
			property.setAvailability(dto.getAvailability());
		if (dto.getFurnishingStatus() != null)
			property.setFurnishingStatus(dto.getFurnishingStatus());

		return PropertyMapper.toDto(propertyRepository.save(property));
	}

	// DELETE PROPERTY
	public void deleteProperty(Long propertyId) {
		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));
		propertyRepository.delete(property);
	}

	// ASSIGN AGENT (ADMIN)
	public void assignAgent(Long propertyId, Long agentId) {

		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));

		User agent = userRepository.findById(agentId).orElseThrow(() -> new RuntimeException("Agent not found"));

		property.setAgent(agent);
		propertyRepository.save(property);
	}

	// ADD AMENITIES
	public void addAmenities(Long propertyId, Set<Long> amenityIds) {

		Property property = propertyRepository.findById(propertyId)
				.orElseThrow(() -> new RuntimeException("Property not found"));

		Set<Amenity> amenities = amenityRepository.findAllById(amenityIds).stream().collect(Collectors.toSet());

		property.setAmenities(amenities);
		propertyRepository.save(property);
	}
}
