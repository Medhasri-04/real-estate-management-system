package com.realestatemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.realestatemanagement.entity.Notification;
import com.realestatemanagement.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUser(User user);
}
