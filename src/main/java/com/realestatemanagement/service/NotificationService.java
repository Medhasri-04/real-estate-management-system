package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.request.NotificationRequestDTO;
import com.realestatemanagement.dto.response.NotificationResponseDTO;
import com.realestatemanagement.entity.Notification;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.mapper.NotificationMapper;
import com.realestatemanagement.repository.NotificationRepository;
import com.realestatemanagement.repository.UserRepository;

@Service
public class NotificationService {
	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;

	public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
	}

	public NotificationResponseDTO createNotification(NotificationRequestDTO dto) {
		User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
		Notification notification = NotificationMapper.toEntity(dto, user);
		notification = notificationRepository.save(notification);
		return NotificationMapper.toDto(notification);
	}

	public List<NotificationResponseDTO> getAllNotifications() {
		return notificationRepository.findAll().stream().map(NotificationMapper::toDto).collect(Collectors.toList());
	}
}
