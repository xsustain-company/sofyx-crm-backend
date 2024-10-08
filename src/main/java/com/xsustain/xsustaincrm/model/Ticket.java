package com.xsustain.xsustaincrm.model;

import com.xsustain.xsustaincrm.model.enume.PipelineType;
import com.xsustain.xsustaincrm.model.enume.PriorityType;
import com.xsustain.xsustaincrm.model.enume.SourceType;
import com.xsustain.xsustaincrm.model.enume.StatusType;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Ticket {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long idTicket;

    private String ticketName; //

    private String description; //

    private LocalDateTime closeDate; //

    private LocalDateTime createdDate; //

    @Enumerated(EnumType.STRING)
    private PipelineType pipeline; //

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Enumerated(EnumType.STRING)
    private PriorityType priority; //

    @Enumerated(EnumType.STRING)
    private SourceType source; //

    private Boolean deleted;

    @ManyToOne
    private User owner; //

    @ManyToMany
    private List<User> assigned; //

}
