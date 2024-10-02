package com.xsustain.xsustaincrm.dto;

import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.enume.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MultiNotifsCreateDto {

    @NotNull(message = "Invalid Notification: Notification is NULL")
    private NotificationsDto notification;

    @NotNull(message = "Invalid userIds: userIds is NULL")
    private List<Long> users;

}
