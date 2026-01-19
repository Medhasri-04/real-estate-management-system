package com.realestatemanagement.mapper;

import com.realestatemanagement.dto.response.AmenityResponse;
import com.realestatemanagement.entity.Amenity;
import org.springframework.stereotype.Component;

@Component
public class AmenityMapper {
	public AmenityResponse toResponse(Amenity a) {
		AmenityResponse r = new AmenityResponse();
		r.setAmenityId(a.getId());
		r.setName(a.getName());
		return r;
	}
}