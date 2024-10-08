package com.xsustain.xsustaincrm.model;

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
public class Pipeline {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long idPipeline;

    private String pipelineName; // Pipeline Name

    private double totalDealValue; // Total Deal Value

    private int numberOfDeals; // No of Deals

    @OneToMany
    private List<Stage> stages; // Stages in the pipeline

    @OneToOne
    private Stage currentStage; // Current Stage

    private LocalDateTime createdDate; // Created Date

    private String status; // Status of the pipeline

    private Boolean deleted;
}
