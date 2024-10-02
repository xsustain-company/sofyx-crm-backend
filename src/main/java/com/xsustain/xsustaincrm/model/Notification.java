package com.xsustain.xsustaincrm.model;

import com.xsustain.xsustaincrm.model.enume.NotificationType;
import com.xsustain.xsustaincrm.model.enume.PriorityType;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Notification {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long idNotification;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private PriorityType priority;

    private boolean viewed;

    private LocalDateTime createdDate;

    private LocalDateTime viewedDate;

    private String link;

    @ManyToOne
    private User user;
}