package com.eseo.academic.repository;

import com.eseo.academic.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Find all notifications for a specific user, ordered by newest first
    List<Notification> findByRecipientEmailOrderByCreatedAtDesc(String recipientEmail);
}