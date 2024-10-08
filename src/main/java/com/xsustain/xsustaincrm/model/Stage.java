package com.xsustain.xsustaincrm.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Stage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String title; // Title of the stage

    private String description; // Description of the stage

    private Boolean deleted;
}