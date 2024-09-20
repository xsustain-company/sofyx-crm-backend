package com.xsustain.xsustaincrm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xsustain.xsustaincrm.model.enume.PermissionType;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Permission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long idPermissions;
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;
    private String description;

    @JsonIgnore
    @ManyToOne
    private User user;

}
