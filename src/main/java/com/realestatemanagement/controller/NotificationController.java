package com.realestatemanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.realestatemanagement.dto.response.NotificationResponseDTO;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	// 48. GET /notifications
	@GetMapping
	public List<NotificationResponseDTO> getMyNotifications(@AuthenticationPrincipal User user) {
		return notificationService.getMyNotifications(user);
	}

	// 49. PATCH /notifications/{id}/read
	@PatchMapping("/{id}/read")
	@ResponseStatus(HttpStatus.OK)
	public void markAsRead(@PathVariable Long id, @AuthenticationPrincipal User user) {

		notificationService.markAsRead(id, user);
	}

	// 50. DELETE /notifications/{id}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNotification(@PathVariable Long id, @AuthenticationPrincipal User user) {

		notificationService.deleteNotification(id, user);
	}
}
