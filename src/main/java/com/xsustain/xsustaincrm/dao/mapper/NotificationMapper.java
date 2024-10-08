package com.xsustain.xsustaincrm.dao.mapper;

import com.xsustain.xsustaincrm.dto.NotificationDto;
import com.xsustain.xsustaincrm.model.Notification;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class NotificationMapper {

    @Autowired
    private ModelMapper modelMapper;

    // Convert Notification JPA Entity into NotificationDto
    public NotificationDto mapToNotificationDto(Notification notification) {
        return modelMapper.map(notification, NotificationDto.class);
    }

    // Convert NotificationDto to Notification JPA Entity
    public Notification mapToNotification(NotificationDto notificationDto) {
        return modelMapper.map(notificationDto, Notification.class);
    }

    // Convert a list of Notification JPA Entities to NotificationDtos
    public List<NotificationDto> mapToNotificationDtoList(List<Notification> notifications) {
        List<NotificationDto> tempList = new ArrayList<>();
        for (Notification notification : notifications) {
            tempList.add(this.mapToNotificationDto(notification));
        }
        return tempList;
    }

    // Convert a list of NotificationDtos to Notification JPA Entities
    public List<Notification> mapToNotificationList(List<NotificationDto> notificationDtos) {
        List<Notification> tempList = new ArrayList<>();
        for (NotificationDto notificationDto : notificationDtos) {
            tempList.add(this.mapToNotification(notificationDto));
        }
        return tempList;
    }
}
