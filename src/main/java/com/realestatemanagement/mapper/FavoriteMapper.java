package com.realestatemanagement.mapper;

import org.springframework.stereotype.Component;

import com.realestatemanagement.dto.response.FavoritePropertyResponse;
import com.realestatemanagement.entity.Favorite;
import com.realestatemanagement.entity.Property;

@Component
public class FavoriteMapper {
	public FavoritePropertyResponse toResponse(Favorite f) {
		FavoritePropertyResponse r = new FavoritePropertyResponse();
		Property p = f.getProperty();
		r.setPropertyId(p != null ? p.getId() : null);
		r.setTitle(p != null ? p.getTitle() : null);
		r.setCity(extractCity(p != null ? p.getLocation() : null));
		return r;
	}

	private String extractCity(String location) {
		if (location == null)
			return null;
		String[] parts = location.split(",");
		return parts[parts.length - 1].trim();
	}
}