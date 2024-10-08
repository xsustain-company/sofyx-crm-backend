package com.xsustain.xsustaincrm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StageDto {

    private long id;

    @NotBlank(message = "Invalid title: Empty title")
    @NotNull(message = "Invalid title: title is NULL")
    private String title; // Title of the stage

    @NotBlank(message = "Invalid description: Empty description")
    @NotNull(message = "Invalid description: description is NULL")
    private String description; // Description of the stage

    private Boolean deleted;
}
