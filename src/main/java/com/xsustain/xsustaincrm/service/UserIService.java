package com.xsustain.xsustaincrm.service;


import com.nylas.models.ListResponse;
import com.nylas.models.Message;
import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.Permission;
import jakarta.mail.MessagingException;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UserIService {
    public UserDto createUserAccount(UserRegisterDTO userRegisterDTO) throws MessagingException;

    public JwtResponse login(String email);

    public ResponseDto validateAccount(Long verificationCode) throws MessagingException;

    public UserDto createUserAccountAndAssignedPermissions(UserRegisterByAdminDTO userRegisterByAdminDTO)
            throws MessagingException;

    public ResponseDto forgotPassword(String email) throws MessagingException;

    public ResponseDto resetPassword(Long token, ResetPasswordRequest newPassword);

    public UserDto getUserById();

    public List<UserDto> getAllUsers();

    public UserUpdateProfile updateProfile(UserUpdateProfile userUpdateProfile, long idUser);

    public ResponseDto deleteUser(long idUser);

    public ResponseDto logout(String email);

}
