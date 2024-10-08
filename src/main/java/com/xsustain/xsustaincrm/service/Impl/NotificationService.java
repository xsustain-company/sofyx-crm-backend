package com.xsustain.xsustaincrm.service.Impl;

import com.xsustain.xsustaincrm.dto.NotificationDto;
import com.xsustain.xsustaincrm.dto.NotificationsDto;
import com.xsustain.xsustaincrm.model.Notification;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.repository.NotificationRepository;
import com.xsustain.xsustaincrm.repository.UserRepository;
import com.xsustain.xsustaincrm.service.NotificationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NotificationService implements NotificationIService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = convertToEntity(notificationDto);
        Notification savedNotification = notificationRepository.save(notification);
        return convertToDto(savedNotification);
    }

    @Override
    public List<NotificationDto> createNotifications(NotificationsDto notificationsDto, List<Long> userIds) {
        // Retrieve users based on userIds
        List<User> users = StreamSupport.stream(userRepository.findAllById(userIds).spliterator(), false)
                .collect(Collectors.toList());

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setTitle(notificationsDto.getTitle());
        notificationDto.setDescription(notificationsDto.getDescription());
        notificationDto.setType(notificationsDto.getType());
        notificationDto.setPriority(notificationsDto.getPriority());
        notificationDto.setViewed(notificationsDto.isViewed());
        notificationDto.setCreatedDate(notificationsDto.getCreatedDate());
        notificationDto.setViewedDate(notificationsDto.getViewedDate());
        notificationDto.setLink(notificationsDto.getLink());

        List<NotificationDto> notificationDtosList = users.stream().map(user -> {
            notificationDto.setUser(user);
            return createNotification(notificationDto);
        }).collect(Collectors.toList());

        return notificationDtosList;

    }

    @Override
    public NotificationDto deleteNotification(long idNotification) {
        Optional<Notification> notificationOpt = notificationRepository.findById(idNotification);
        if (notificationOpt.isPresent()) {
            notificationRepository.deleteById(idNotification);
            return convertToDto(notificationOpt.get());
        } else {
            throw new RuntimeException("Notification not found");
        }
    }

    @Override
    public NotificationDto getOneNotificationByUser(long idNotification, long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Optional<Notification> notificationOpt = notificationRepository.findByIdAndUser(idNotification, userOpt.get());
        if (notificationOpt.isPresent()) {
            return convertToDto(notificationOpt.get());
        } else {
            throw new RuntimeException("Notification not found for this user");
        }
    }

    @Override
    public List<Notification> getNotificationsByUserId(long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return notificationRepository.findByUser(userOpt.get());
    }

    // Utility method to convert DTO to entity
    private Notification convertToEntity(NotificationDto notificationDto) {
        // Conversion logic from NotificationDto to Notification
        Notification notification = new Notification();
        notification.setTitle(notificationDto.getTitle());
        notification.setDescription(notificationDto.getDescription());
        notification.setType(notificationDto.getType());
        notification.setPriority(notificationDto.getPriority());
        notification.setViewed(notificationDto.isViewed());
        notification.setCreatedDate(notificationDto.getCreatedDate());
        notification.setViewedDate(notificationDto.getViewedDate());
        notification.setLink(notificationDto.getLink());
        notification.setUser(notificationDto.getUser());
        return notification;
    }

    // Utility method to convert entity to DTO
    private NotificationDto convertToDto(Notification notification) {
        // Conversion logic from Notification to NotificationDto
        return NotificationDto.builder()
                .idNotification(notification.getIdNotification())
                .title(notification.getTitle())
                .description(notification.getDescription())
                .type(notification.getType())
                .priority(notification.getPriority())
                .viewed(notification.isViewed())
                .createdDate(notification.getCreatedDate())
                .viewedDate(notification.getViewedDate())
                .link(notification.getLink())
                .user(notification.getUser())
                .build();
    }
}