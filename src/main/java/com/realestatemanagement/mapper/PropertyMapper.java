package com.realestatemanagement.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.realestatemanagement.dto.request.PropertyRequestDTO;
import com.realestatemanagement.dto.response.PropertyResponseDTO;
import com.realestatemanagement.entity.Amenity;
import com.realestatemanagement.entity.Property;
import com.realestatemanagement.entity.User;

public class PropertyMapper {

	public static PropertyResponseDTO toDto(Property property) {
		if (property == null)
			return null;

		PropertyResponseDTO dto = new PropertyResponseDTO();
		dto.setId(property.getId());
		dto.setTitle(property.getTitle());
		dto.setDescription(property.getDescription());
		dto.setPrice(property.getPrice());
		dto.setLocation(property.getLocation());
		dto.setPropertyType(property.getPropertyType());
		dto.setAvailability(property.getAvailability());
		dto.setPropertyAge(property.getPropertyAge());
		dto.setFurnishingStatus(property.getFurnishingStatus());
		dto.setLatitude(property.getLatitude());
		dto.setLongitude(property.getLongitude());

		dto.setAgentName(Optional.ofNullable(property.getAgent()).map(a -> a.getFirstName() + " " + a.getLastName())
				.orElse(null));

		dto.setAmenities(Optional.ofNullable(property.getAmenities())
				.map(amenities -> amenities.stream().map(Amenity::getName).collect(Collectors.toSet())).orElse(null));

		dto.setCreatedAt(property.getCreatedAt());
		dto.setUpdatedAt(property.getUpdatedAt());
		return dto;
	}

	public static Property toEntity(PropertyRequestDTO dto, User agent, Set<Amenity> amenities) {
		if (dto == null)
			return null;

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
		property.setAmenities(amenities);
		return property;
	}
}
