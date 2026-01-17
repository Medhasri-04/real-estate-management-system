package com.realestatemanagement.mapper;

import com.realestatemanagement.entity.Amenity;
import com.realestatemanagement.dto.request.AmenityRequestDTO;
import com.realestatemanagement.dto.response.AmenityResponseDTO;

public class AmenityMapper {

	public static AmenityResponseDTO toDto(Amenity amenity) {
		if (amenity == null)
			return null;

		AmenityResponseDTO dto = new AmenityResponseDTO();
		dto.setId(amenity.getId());
		dto.setName(amenity.getName());
		return dto;
	}

	public static Amenity toEntity(AmenityRequestDTO dto) {
		if (dto == null)
			return null;

		Amenity amenity = new Amenity();
		amenity.setName(dto.getName());
		return amenity;
	}
}
