package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.response.FavoritePropertyResponse;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.entity.Favorite;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.FavoriteMapper;
import com.realestatemanagement.repository.FavoriteRepository;
import com.realestatemanagement.repository.PropertyRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class FavoriteService {
	private final FavoriteRepository favoriteRepo;
	private final PropertyRepository propertyRepo;
	private final UserRepository userRepo;
	private final FavoriteMapper mapper;

	public FavoriteService(FavoriteRepository favoriteRepo, PropertyRepository propertyRepo, UserRepository userRepo,
			FavoriteMapper mapper) {
		this.favoriteRepo = favoriteRepo;
		this.propertyRepo = propertyRepo;
		this.userRepo = userRepo;
		this.mapper = mapper;
	}

	private User currentUser() {
		return userRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();
	}

	public MessageResponse add(Long propertyId) {
		User u = currentUser();
		if (favoriteRepo.existsByUserIdAndPropertyId(u.getId(), propertyId)) {
			throw new RuntimeException("Already favorited");
		}
		Favorite f = new Favorite();
		f.setUser(u);
		f.setProperty(propertyRepo.findById(propertyId).orElseThrow());
		favoriteRepo.save(f);
		return new MessageResponse("Added to favorites", LocalDateTime.now());
	}

	public void remove(Long propertyId) {
		favoriteRepo.deleteByUserIdAndPropertyId(currentUser().getId(), propertyId);
	}

	public List<FavoritePropertyResponse> list() {
		List<FavoritePropertyResponse> res = new ArrayList<>();
		for (Favorite f : favoriteRepo.findByUserId(currentUser().getId())) {
			res.add(mapper.toResponse(f));
		}
		return res;
	}
}