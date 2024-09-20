package com.xsustain.xsustaincrm.dao.mapper;

import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.User;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    // convert User Jpa Entity into UserDTO
    public UserDto mapToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    // Convert UserDTO to User JPA Entity
    public User mapToUser(UserRegisterDTO userRegisterDTO) {
        return modelMapper.map(userRegisterDTO, User.class);
    }

    public User mapToUser(UserRegisterByAdminDTO userRegisterByAdminDTO) {
        return modelMapper.map(userRegisterByAdminDTO, User.class);
    }
    public User mapToUserUpdateProfile(UserUpdateProfile userUpdateProfile) {
        return modelMapper.map(userUpdateProfile, User.class);
    }

    // Convert UserDTO to User JPA Entity
    public UserResponse mapToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

}