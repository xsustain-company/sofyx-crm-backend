package com.xsustain.xsustaincrm.dto;


import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.enume.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {
    private long idUser;
    @NotBlank(message = "Invalid firstName: Empty firstName")
    @NotNull(message = "Invalid firstName: firstName is NULL")
    @Email(message = "Invalid email")
    private String firstName;
    @NotBlank(message = "Invalid lastName: Empty lastName")
    @NotNull(message = "Invalid lastName: lastName is NULL")
    @Email(message = "Invalid email")
    private String lastName;
    @NotBlank(message = "Invalid Email: Empty Email")
    @NotNull(message = "Invalid Email: Email is NULL")
    @Email(message = "Invalid email")
    private String email;
    @NotNull(message = "Invalid role type: roleType is NULL")
    private RoleType roleTypes;

    List<Permission> permissionList;
}
