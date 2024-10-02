package com.xsustain.xsustaincrm.dto;

import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.model.enume.NotificationType;
import com.xsustain.xsustaincrm.model.enume.PriorityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NotificationsDto {

    private long idNotification;

    @NotBlank(message = "Invalid title: Empty title")
    @NotNull(message = "Invalid title: title is NULL")
    private String title;

    @NotBlank(message = "Invalid description: Empty description")
    @NotNull(message = "Invalid description: description is NULL")
    private String description;

    @NotNull(message = "Invalid type: type is NULL")
    private NotificationType type;

    @NotNull(message = "Invalid priority: priority is NULL")
    private PriorityType priority;

    @NotNull(message = "Invalid createdDate: createdDate is NULL")
    private LocalDateTime createdDate;

    private boolean viewed;

    private LocalDateTime viewedDate;

    @NotBlank(message = "Invalid link: Empty link")
    @NotNull(message = "Invalid link: link is NULL")
    private String link;
}