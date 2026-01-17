package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.PropertyRequestDTO;
import com.realestatemanagement.dto.response.PropertyResponseDTO;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.PropertyMapper;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class PropertyService {
	private final PropertyRepository propertyRepository;
	private final UserRepository userRepository;

	public PropertyService(PropertyRepository propertyRepository, UserRepository userRepository) {
		this.propertyRepository = propertyRepository;
		this.userRepository = userRepository;
	}

	// Create property
	public PropertyResponseDTO createProperty(PropertyRequestDTO dto, Long agentId) {
		User agent = userRepository.findById(agentId).orElseThrow(() -> new RuntimeException("Agent not found"));
		Property property = new Property();
		property.setTitle(dto.getTitle());
		property.setDescription(dto.getDescription());
		property.setPrice(dto.getPrice());
		property.setLocation(dto.getLocation());
		property.setPropertyType(dto.getPropertyType());
		property.setAvailability(dto.getAvailability());
		property.setPropertyAge(dto.getPropertyAge());
		property.setFurnishingStatus(dto.getFurnishingStatus());
		property.setLatitude(dto.getLatitude());
		property.setLongitude(dto.getLongitude());
		property.setAgent(agent);

		return PropertyMapper.toDto(propertyRepository.save(property));
	}

	// Get property by ID
	public PropertyResponseDTO getPropertyById(Long id) {
		return propertyRepository.findById(id).map(PropertyMapper::toDto).orElse(null);
	}

	// Get all properties
	public List<PropertyResponseDTO> getAllProperties() {
		return propertyRepository.findAll().stream().map(PropertyMapper::toDto).collect(Collectors.toList());
	}

	// Delete property
	public void deleteProperty(Long id) {
		propertyRepository.deleteById(id);
	}
}
