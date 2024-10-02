package com.xsustain.xsustaincrm.service;

import com.xsustain.xsustaincrm.dto.NotificationDto;
import com.xsustain.xsustaincrm.dto.NotificationsDto;
import com.xsustain.xsustaincrm.model.Notification;

import java.util.List;

public interface NotificationIService {

    // Create a notification
    public NotificationDto createNotification(NotificationDto notificationDto);

    // Create multiple notifications
    public List<NotificationDto> createNotifications(NotificationsDto notificationDto, List<Long> userIds);

    // Delete a notification by its ID
    public NotificationDto deleteNotification(long idNotification);

    // Get a single notification by its ID and user ID
    public NotificationDto getOneNotificationByUser(long idNotification, long userId);

    // Get all notifications for a specific user by user ID
    public List<Notification> getNotificationsByUserId(long userId);
}