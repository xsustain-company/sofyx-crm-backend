package com.xsustain.xsustaincrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PipelineDto {

    private long idPipeline;

    @NotBlank(message = "Invalid pipelineName: Empty pipelineName")
    @NotNull(message = "Invalid pipelineName: pipelineName is NULL")
    private String pipelineName; // Pipeline Name

    @NotNull(message = "Invalid totalDealValue: totalDealValue is NULL")
    private double totalDealValue; // Total Deal Value

    @NotNull(message = "Invalid numberOfDeals: numberOfDeals is NULL")
    private int numberOfDeals; // Number of Deals

    @NotNull(message = "Invalid stages: stages is NULL")
    private List<StageDto> stages; // Stages in the pipeline

    @NotNull(message = "Invalid currentStage: currentStage is NULL")
    private StageDto currentStage; // Current Stage

    @NotNull(message = "Invalid createdDate: createdDate is NULL")
    private LocalDateTime createdDate; // Created Date

    @NotBlank(message = "Invalid status: Empty status")
    @NotNull(message = "Invalid status: status is NULL")
    private String status; // Status of the pipeline

    private Boolean deleted;
}
