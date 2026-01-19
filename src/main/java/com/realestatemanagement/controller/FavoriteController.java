package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.response.FavoritePropertyResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.service.FavoriteService;

@RestController
public class FavoriteController {
	private final FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	// 45. POST /properties/{propertyId}/favorite
	@PostMapping("/properties/{propertyId}/favorite")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse add(@PathVariable Long propertyId) {
		return favoriteService.add(propertyId);
	}

	// 46. DELETE /properties/{propertyId}/favorite
	@DeleteMapping("/properties/{propertyId}/favorite")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long propertyId) {
		favoriteService.remove(propertyId);
	}

	// 47. GET /me/favorites
	@GetMapping("/me/favorites")
	public List<FavoritePropertyResponse> list() {
		return favoriteService.list();
	}
}