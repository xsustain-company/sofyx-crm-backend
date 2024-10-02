package com.xsustain.xsustaincrm.model;

import com.xsustain.xsustaincrm.model.enume.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long idUser;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String phoneNumber;
    private String grandId;
    private Instant creationDate;
    private boolean validated;
    private Long tokenToValidate;
    private Long tokenToForgotPassword;
    private LocalDateTime tokenToForgotPasswordCreationDate;
    private LocalDateTime validateCodeCreationDate;
    private LocalDateTime lastLogin;
    private boolean activeUser;

    @Enumerated(EnumType.STRING)
    private RoleType roleTypes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Permission> permissionList;
}
