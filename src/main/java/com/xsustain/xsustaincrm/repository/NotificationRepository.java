package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.Notification;
import com.xsustain.xsustaincrm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Fetch all notifications for a specific user
    List<Notification> findByUser(User user);

    // Fetch a single notification for a specific user by notification ID
    Optional<Notification> findByIdAndUser(Long id, User user);
}