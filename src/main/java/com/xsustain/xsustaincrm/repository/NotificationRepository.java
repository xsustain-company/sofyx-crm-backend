package com.xsustain.xsustaincrm.repository;

import com.xsustain.xsustaincrm.model.Notification;
import com.xsustain.xsustaincrm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.user = ?1")
    List<Notification> findByUser(User user);

    @Query("SELECT n FROM Notification n WHERE n.id = ?1 AND n.user = ?2")
    Optional<Notification> findByIdAndUser(Long id, User user);
}