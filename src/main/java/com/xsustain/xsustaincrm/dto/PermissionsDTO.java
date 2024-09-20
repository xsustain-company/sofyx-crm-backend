package com.xsustain.xsustaincrm.dto;

import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.model.enume.PermissionType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionsDTO {
    private PermissionType permissionType;
    private String description;
    private User user;
}
