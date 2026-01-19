package com.realestatemanagement.mapper;

import org.springframework.stereotype.Component;

import com.realestatemanagement.dto.response.NotificationResponse;
import com.realestatemanagement.entity.Notification;

@Component
public class NotificationMapper {
	public NotificationResponse toResponse(Notification n) {
		NotificationResponse r = new NotificationResponse();
		r.setNotificationId(n.getId());
		r.setMessage(n.getMessage());
		r.setType(n.getType());
		r.setSent(n.isSent());
		r.setRead(n.isRead());
		r.setCreatedAt(n.getCreatedAt());
		return r;
	}
}