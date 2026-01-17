package com.realestatemanagement.mapper;

import com.realestatemanagement.dto.request.NotificationRequestDTO;
import com.realestatemanagement.dto.response.NotificationResponseDTO;
import com.realestatemanagement.entity.Notification;
import com.realestatemanagement.entity.User;

public class NotificationMapper {

    public static NotificationResponseDTO toDto(Notification notification) {
        if (notification == null) return null;

        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setSent(notification.getSent());

        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getId());
            dto.setUserName(
                notification.getUser().getFirstName() + " " +
                notification.getUser().getLastName()
            );
        }

        dto.setCreatedAt(notification.getCreatedAt());
        return dto;
    }

    public static Notification toEntity(NotificationRequestDTO dto, User user) {
        if (dto == null) return null;

        Notification notification = new Notification();
        notification.setMessage(dto.getMessage());
        notification.setType(dto.getType());
        notification.setSent(dto.getSent());
        notification.setUser(user);
        return notification;
    }
}
