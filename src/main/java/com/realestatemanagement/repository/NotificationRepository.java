package com.realestatemanagement.repository;

import com.realestatemanagement.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // GET /notifications

    List<Notification> findByUserId(Long userId);

}
 