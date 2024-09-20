package com.xsustain.xsustaincrm.dto;


import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.enume.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePicture;
    private RoleType roleTypes;
    private List<Permission> permissionList;
}