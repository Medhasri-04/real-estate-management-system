package com.realestatemanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.realestatemanagement.dto.response.NotificationResponseDTO;
import com.realestatemanagement.entity.Notification;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.repository.NotificationRepository;

@Service
public class NotificationService {

	private final NotificationRepository notificationRepository;

	public NotificationService(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	// GET all notifications of logged-in user
	public List<NotificationResponseDTO> getMyNotifications(User user) {

		return notificationRepository.findByUser(user).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	// MARK notification as READ
	public void markAsRead(Long notificationId, User user) {

		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		if (!notification.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Unauthorized access");
		}

		notification.setRead(true);
		notificationRepository.save(notification);
	}

	// DELETE notification
	public void deleteNotification(Long notificationId, User user) {

		Notification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		if (!notification.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Unauthorized access");
		}

		notificationRepository.delete(notification);
	}

	// Mapper method
	private NotificationResponseDTO mapToDto(Notification notification) {

		NotificationResponseDTO dto = new NotificationResponseDTO();
		dto.setId(notification.getId());
		dto.setMessage(notification.getMessage());
		dto.setType(notification.getType());
		dto.setSent(notification.getSent());
		dto.setRead(notification.getRead());
		dto.setCreatedAt(notification.getCreatedAt());

		return dto;
	}
}
