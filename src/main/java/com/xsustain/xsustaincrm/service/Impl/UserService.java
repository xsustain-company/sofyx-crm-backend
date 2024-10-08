package com.xsustain.xsustaincrm.service.Impl;

import com.nylas.models.CodeExchangeResponse;
import com.nylas.models.ListResponse;
import com.nylas.models.Message;
import com.xsustain.xsustaincrm.constant.AuthenticationConstants;
import com.xsustain.xsustaincrm.dao.mapper.UserMapper;
import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.exception.UserServiceCustomException;
import com.xsustain.xsustaincrm.model.Permission;
import com.xsustain.xsustaincrm.model.User;
import com.xsustain.xsustaincrm.model.enume.RoleType;
import com.xsustain.xsustaincrm.repository.PermissionsRepository;
import com.xsustain.xsustaincrm.repository.UserRepository;
import com.xsustain.xsustaincrm.service.UserIService;
import com.xsustain.xsustaincrm.utility.EmailUtility;
import com.xsustain.xsustaincrm.utility.ResponseUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class UserService implements UserIService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;

    @Autowired
    EmailUtility emailUtility;
    @Autowired
    JWTService jwtService;

    @Autowired
    ResponseUtil responseUtil;
    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    SessionService sessionService;
    private Random random = new Random();


    @Override
    public UserDto createUserAccount(UserRegisterDTO userRegisterDTO) throws MessagingException {
        return createAccount(userRegisterDTO,userRegisterDTO.getRoleTypes());
    }

    @Override
    public JwtResponse login(String email) {
        String token = null;
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserServiceCustomException("No user found with this email:" + email, "User_Not_Found"));
        if (!user.isValidated()) {
            throw new UserServiceCustomException("You need to validate your account first and check your email",
                    "ACCOUNT_NOT_VALID", HttpStatus.FORBIDDEN);
        } else {
            token = jwtService.generateToken(email);
            UserResponse userResponse = userMapper.mapToUserResponse(user);
            user.setActiveUser(true);
            userRepository.save(user);
            return new JwtResponse(userResponse, token);
        }
    }

    @Override
    public ResponseDto validateAccount(Long verificationCode) throws MessagingException {
        User user=userRepository.findByTokenToValidate(verificationCode);
        if (Boolean.FALSE.equals(user.getTokenToValidate()) && !user.getTokenToValidate().equals(verificationCode)) {
            throw new UserServiceCustomException("Invalid verification code!", "INVALID_CODE");
        }
        if (isTokenExpired(user.getValidateCodeCreationDate())) {
            user.setTokenToValidate(generateOTPToSend());
            user.setValidateCodeCreationDate(LocalDateTime.now());
            user = userRepository.save(user);
            emailUtility.sendVerificationEmail(user.getEmail(), user.getFirstName(), user.getTokenToValidate(),
                    user.getRoleTypes());
            return responseUtil.createResponse("Current token experied!! Sent a new Token", "SUCCESS",
                    "Check your email :" + user.getEmail()
                            + " and OTP token to reset your password. If you didn't receive contact support team.");
        }
        if (Boolean.TRUE.equals(user.getTokenToValidate())) {
            return responseUtil.createResponse("Your account is already validated", "SUCCESS",
                    "Account already validated");
        }
        user.setValidated(true);
        user.setTokenToValidate(null);
        userRepository.save(user);
        return responseUtil.createResponse("Your account is validated", "SUCCESS", "Account validated successfully");
    }

    @Override
    public UserDto createUserAccountAndAssignedPermissions(UserRegisterByAdminDTO userRegisterByAdminDTO) throws MessagingException {
        UserDto userDto = createAccountByAdmin(userRegisterByAdminDTO, userRegisterByAdminDTO.getRoleTypes());
        User user = userRepository.findUserByEmail(userRegisterByAdminDTO.getEmail()).get();
        List<Permission> permissionList = userRegisterByAdminDTO.getPermissionList();
        if (permissionList != null && !permissionList.isEmpty()) {
            for (Permission permission : permissionList) {
                permission.setUser(user);
                permissionsRepository.save(permission);
            }
        }
        return userDto;
    }


   


    @Override
    public ResponseDto forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceCustomException("No user found with this email: " + email,
                        "USER_NOT_FOUND", HttpStatus.NOT_FOUND));
        if (Boolean.FALSE.equals(user.isValidated())) {
            throw new UserServiceCustomException("You need to validate your account first.",
                    "ACCOUNT_NOT_VALID", HttpStatus.FORBIDDEN);
        }
        if (user.getTokenToForgotPassword() != null && !isTokenExpired(user.getTokenToForgotPasswordCreationDate())) {
            return responseUtil.createResponse("Your forgot password OTP was already sent", "OTP_ALREADY_SENT",
                    "Check your email :" + email + "and OTP token to reset your password");
        }

        user.setTokenToForgotPassword(generateOTPToSend());
        user.setTokenToForgotPasswordCreationDate(LocalDateTime.now());
        user = userRepository.save(user);
        emailUtility.sendForgetPasswordEmail(user.getEmail(), user.getFirstName(), user.getTokenToForgotPassword());
        return responseUtil.createResponse("Your Token is successfully sended", "SUCCESS",
                "Check your email :" + email + "and OTP token to reset your password");
    }

    @Override
    public ResponseDto resetPassword(Long token, ResetPasswordRequest newPassword) {
        User user=userRepository.findByTokenToForgotPassword(token);
        if (!Objects.equals(user.getTokenToForgotPassword(), token)) {
            throw new UserServiceCustomException("Token Invalid", "Token invalid");
        }
        LocalDateTime tokenCreationDate = user.getTokenToForgotPasswordCreationDate();
        if (isTokenExpired(tokenCreationDate)) {
            return responseUtil.createResponse("Token expired", "Failed", "Token expired");
        }
        System.out.println(newPassword);
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        user.setTokenToForgotPassword(null);
        user.setTokenToForgotPasswordCreationDate(null);
        userRepository.save(user);
        return responseUtil.createResponse("Password Updated", "SUCCESS", "Your password successfully updated.");
    }

    @Override
    public UserDto getUserById() {
        User user=sessionService.getUserBySession().get();
        return userMapper.mapToUserDto(user);
    }

    @Override
    public UserUpdateProfile updateProfile(UserUpdateProfile userUpdateProfile, long idUser) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new UserServiceCustomException("User not found with id: " + idUser ,"USER NOT FOUNDQ"));
        User user1 = userMapper.mapToUserUpdateProfile(userUpdateProfile);

        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setPhoneNumber(user1.getPhoneNumber());
        user.setEmail(user1.getEmail());

        if (user.getPermissionList() != null) {
            user.getPermissionList().clear();
            user.getPermissionList().addAll(user1.getPermissionList());
        } else {
            user.setPermissionList(user1.getPermissionList());
        }
        userRepository.save(user);
        return userUpdateProfile;
    }

    @Override
    public ResponseDto deleteUser(long idUser) {
        userRepository.deleteById(idUser);
        return responseUtil.createResponse("User deleted", "SUCCESS", "Your user deleted with succes.");
    }

    @Override
    public ResponseDto logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceCustomException("No user found with this email: " + email,
                        "USER_NOT_FOUND", HttpStatus.NOT_FOUND));
        user.setActiveUser(false);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return responseUtil.createResponse("User logout", "SUCCESS", "Your account logout with succes.");
    }


    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        return diff.toMinutes() >= AuthenticationConstants.EXPIRE_TOKEN_AFTER_MINUTES;
    }
    public UserDto createAccount(UserRegisterDTO userRegisterDTO, RoleType roleType) throws MessagingException {
        
        if(userRepository.existsByEmail(userRegisterDTO.getEmail())){
            throw new UserServiceCustomException("Email must be unique","DUPLICATED_EMAIL");
        }
        
        User user=userMapper.mapToUser(userRegisterDTO);
        user.setCreationDate(Instant.now());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setValidated(false);
        user.setRoleTypes(roleType);
        user.setTokenToValidate(generateOTPToSend());
        user.setValidateCodeCreationDate(LocalDateTime.now());
        user.setActiveUser(false);
        User userSave=userRepository.save(user);
        UserDto userDTO=userMapper.mapToUserDto(userSave);
        emailUtility.sendVerificationEmail(userRegisterDTO.getEmail(), userRegisterDTO.getFirstName(),
                user.getTokenToValidate(), user.getRoleTypes());
        return userDTO;
    }
    public UserDto createAccountByAdmin(UserRegisterByAdminDTO userRegisterByAdminDTO, RoleType roleType) throws MessagingException {
        if(userRepository.existsByEmail(userRegisterByAdminDTO.getEmail())){
            throw new UserServiceCustomException("Email must be unique","DUPLICATED_EMAIL");
        }
        User user=userMapper.mapToUser(userRegisterByAdminDTO);
        user.setCreationDate(Instant.now());
        user.setPassword(passwordEncoder.encode(userRegisterByAdminDTO.getPassword()));
        user.setValidated(false);
        user.setRoleTypes(roleType);
        user.setTokenToValidate(generateOTPToSend());
        user.setValidateCodeCreationDate(LocalDateTime.now());
        user.setActiveUser(false);
        User userSave=userRepository.save(user);
        UserDto userDTO=userMapper.mapToUserDto(userSave);
        emailUtility.sendVerificationEmail(userRegisterByAdminDTO.getEmail(), userRegisterByAdminDTO.getFirstName(),
                user.getTokenToValidate(), user.getRoleTypes());
        return userDTO;
    }
    private Long generateOTPToSend() {
        int min = 10000;
        int max = 99999;
        return Long.valueOf(this.random.nextInt(max - min + 1) + min);
    }

}
