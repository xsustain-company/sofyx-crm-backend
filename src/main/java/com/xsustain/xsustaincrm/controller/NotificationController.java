package com.xsustain.xsustaincrm.controller;

import com.xsustain.xsustaincrm.dao.mapper.NotificationMapper;
import com.xsustain.xsustaincrm.dto.NotificationDto;
import com.xsustain.xsustaincrm.dto.NotificationsDto;
import com.xsustain.xsustaincrm.service.NotificationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationIService notificationService;

    @Autowired
    NotificationMapper notificationMapper;

    // Create a notification for multiple users
    @PostMapping("/create-multiple")
    public ResponseEntity<List<NotificationDto>> createNotifications(
            @RequestBody NotificationsDto notificationsDto,
            @RequestParam List<Long> userIds) {
        List<NotificationDto> createdNotifications = notificationService.createNotifications(notificationsDto, userIds);
        return ResponseEntity.ok(createdNotifications);
    }

    @PostMapping("/create-single")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDto) {
        NotificationDto createdNotification = notificationService.createNotification(notificationDto);
        return ResponseEntity.ok(createdNotification);
    }

    // Delete a notification by its ID
    @DeleteMapping("/delete/{idNotification}")
    public ResponseEntity<NotificationDto> deleteNotification(@PathVariable long idNotification) {
        NotificationDto deletedNotification = notificationService.deleteNotification(idNotification);
        return ResponseEntity.ok(deletedNotification);
    }

    // Get a single notification by notification ID and user ID
    @GetMapping("/get-one/{idNotification}/user/{userId}")
    public ResponseEntity<NotificationDto> getNotificationByUser(
            @PathVariable long idNotification,
            @PathVariable long userId) {
        NotificationDto notification = notificationService.getOneNotificationByUser(idNotification, userId);
        return ResponseEntity.ok(notification);
    }

    // Get all notifications for a specific user by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByUserId(@PathVariable long userId) {
        List<NotificationDto> notifications = notificationMapper
                .mapToNotificationDtoList(notificationService.getNotificationsByUserId(userId));
        return ResponseEntity.ok(notifications);
    }
}
