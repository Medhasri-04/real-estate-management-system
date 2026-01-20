package com.realestatemanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.NotificationResponse;
import com.realestatemanagement.entity.Notification;
import com.realestatemanagement.mapper.NotificationMapper;
import com.realestatemanagement.repository.NotificationRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class NotificationService {
	private final NotificationRepository notificationRepo;
	private final UserRepository userRepo;
	private final NotificationMapper mapper;

	public NotificationService(NotificationRepository notificationRepo, UserRepository userRepo,
			NotificationMapper mapper) {
		this.notificationRepo = notificationRepo;
		this.userRepo = userRepo;
		this.mapper = mapper;
	}

	private Long currentUserId() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByEmail(email).orElseThrow().getId();
	}

	// GET 
	public List<NotificationResponse> list() {
		List<NotificationResponse> res = new ArrayList<>();
		for (Notification n : notificationRepo.findByUserId(currentUserId())) {
			res.add(mapper.toResponse(n));
		}
		return res;
	}

	// PATCH 
	public MessageResponse markRead(Long id) {
		Notification n = notificationRepo.findById(id).orElseThrow();
		n.setSent(true);
		notificationRepo.save(n);
		return new MessageResponse("Notification marked as read", LocalDateTime.now());
	}

	//DELETE 
	public void delete(Long id) {
		notificationRepo.deleteById(id);
	}
}