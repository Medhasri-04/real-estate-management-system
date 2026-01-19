package com.realestatemanagement.dto.response;

import java.time.LocalDateTime;

public class NotificationResponse {
	private Long notificationId;
	private String message;
	private String type;
	private boolean sent;
	private boolean read;
	private LocalDateTime createdAt;

	public NotificationResponse() {
	}

	public NotificationResponse(Long notificationId, String message, String type, boolean sent, boolean read,
			LocalDateTime createdAt) {
		this.notificationId = notificationId;
		this.message = message;
		this.type = type;
		this.sent = sent;
		this.read = read;
		this.createdAt = createdAt;
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}