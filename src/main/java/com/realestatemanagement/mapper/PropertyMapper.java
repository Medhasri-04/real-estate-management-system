package com.realestatemanagement.mapper;

import org.springframework.stereotype.Component;

import com.realestatemanagement.dto.response.PropertyResponse;
import com.realestatemanagement.entity.Property;

@Component
public class PropertyMapper {
	public PropertyResponse toResponse(Property p) {
		PropertyResponse r = new PropertyResponse();
		r.setPropertyId(p.getId());
		r.setTitle(p.getTitle());
		r.setDescription(p.getDescription());
		r.setPrice(p.getPrice());
		r.setLocation(p.getLocation());
		r.setPropertyType(p.getPropertyType());
		r.setAvailability(p.getAvailability());
		r.setPropertyAge(p.getPropertyAge());
		r.setFurnishingStatus(p.getFurnishingStatus());
		r.setLatitude(p.getLatitude());
		r.setLongitude(p.getLongitude());
		r.setCreatedAt(p.getCreatedAt());
		r.setUpdatedAt(p.getUpdatedAt());
		r.setAgentId(p.getAgent() != null ? p.getAgent().getId() : null);
		return r;
	}
}