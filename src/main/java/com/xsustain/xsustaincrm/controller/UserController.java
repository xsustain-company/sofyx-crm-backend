package com.xsustain.xsustaincrm.controller;

import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.exception.UserServiceCustomException;
import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.service.UserIService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/")
public class UserController {
    @Autowired
    UserIService userIService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/user/register")
    public UserDto createUserAccount(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws MessagingException {
        return userIService.createUserAccount(userRegisterDTO);
    }

    @PostMapping("/user/registerFromAdmin")
    public UserDto createUserAndAssignedPermissions(@Valid @RequestBody UserRegisterByAdminDTO userRegisterByAdminDTO)
            throws MessagingException {
        return userIService.createUserAccountAndAssignedPermissions(userRegisterByAdminDTO);
    }

    @PostMapping("/token")
    public JwtResponse login(@Valid @RequestBody LoginRequestDTO loginData) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword()));
            return userIService.login(loginData.getEmail());
        } catch (AuthenticationException e) {
            throw new UserServiceCustomException("Invalid Information", "BAD_LOGIN_CREDENTIALS");
        }
    }

    @PutMapping("/validateAccount/{verificationCode}")
    public ResponseDto validateAccount(@PathVariable Long verificationCode) throws MessagingException {
        return userIService.validateAccount(verificationCode);
    }

    @PostMapping("/forgotPassword")
    public ResponseDto forgotPassword(
            @NotBlank(message = "Invalid Email: Empty Email") @NotNull(message = "Invalid Email: Email is NULL") @Email(message = "Invalid email") @RequestParam String email)
            throws MessagingException {
        return userIService.forgotPassword(email);
    }

    @PutMapping("/resetPassword/{token}")
    public ResponseDto resetPassword(@PathVariable Long token, @RequestBody ResetPasswordRequest password) {
        return userIService.resetPassword(token, password);
    }

    @GetMapping("/getUserById")
    public UserDto getUserById() {
        return userIService.getUserById();
    }

    @PutMapping("/update")
    public UserUpdateProfile updateProfile(@Valid @RequestBody UserUpdateProfile userUpdateProfile,
            @RequestParam long idUser) {
        return userIService.updateProfile(userUpdateProfile, idUser);
    }

    @PutMapping("/logout")
    public ResponseDto logout(@RequestParam String email) {
        return userIService.logout(email);
    }
}
