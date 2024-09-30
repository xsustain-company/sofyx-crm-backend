package com.xsustain.xsustaincrm.dto;

import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.model.enume.PipelineType;
import com.xsustain.xsustaincrm.model.enume.PriorityType;
import com.xsustain.xsustaincrm.model.enume.RoleType;
import com.xsustain.xsustaincrm.model.enume.SourceType;
import com.xsustain.xsustaincrm.model.enume.StatusType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class TicketDto {

    private long idTicket;

    @NotBlank(message = "Invalid ticketName: Empty ticketName")
    @NotNull(message = "Invalid ticketName: ticketName is NULL")
    private String ticketName;

    @NotBlank(message = "Invalid description: Empty description")
    @NotNull(message = "Invalid description: description is NULL")
    private String description;

    @NotNull(message = "Invalid closeDate: closeDate is NULL")
    private LocalDateTime closeDate;

    @NotNull(message = "Invalid createdDate: createdDate is NULL")
    private LocalDateTime createdDate;

    // @NotBlank(message = "Invalid pipeline: Empty pipeline")
    @NotNull(message = "Invalid pipeline: pipeline is NULL")
    private PipelineType pipeline;

    // @NotBlank(message = "Invalid status: Empty status")
    @NotNull(message = "Invalid status: status is NULL")
    private StatusType status;

    // @NotBlank(message = "Invalid priority: Empty priority")
    @NotNull(message = "Invalid priority: priority is NULL")
    private PriorityType priority;

    // @NotBlank(message = "Invalid source: Empty source")
    @NotNull(message = "Invalid source: source is NULL")
    private SourceType source;

    // @NotBlank(message = "Invalid owner: Empty owner")
    @NotNull(message = "Invalid owner: owner is NULL")
    private User owner;

    // @NotBlank(message = "Invalid assigned: Empty assigned")
    @NotNull(message = "Invalid assigned: assigned is NULL")
    private List<User> assigned;

}
