package com.xsustain.xsustaincrm.dao.mapper;

import com.xsustain.xsustaincrm.dto.*;
import com.xsustain.xsustaincrm.model.Ticket;
import com.xsustain.xsustaincrm.model.User;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TicketMapper {

    @Autowired
    private ModelMapper modelMapper;

    // convert Ticket Jpa Entity into TicketDTO
    public TicketDto mapToTicketDto(Ticket ticket) {
        return modelMapper.map(ticket, TicketDto.class);
    }

    // // Convert UserDTO to User JPA Entity
    // public Ticket mapToUser(UserRegisterDTO userRegisterDTO) {
    // return modelMapper.map(userRegisterDTO, Ticket.class);
    // }

    // public User mapToUser(UserRegisterByAdminDTO userRegisterByAdminDTO) {
    // return modelMapper.map(userRegisterByAdminDTO, User.class);
    // }

    // public User mapToUserUpdateProfile(UserUpdateProfile userUpdateProfile) {
    // return modelMapper.map(userUpdateProfile, User.class);
    // }

    // // Convert UserDTO to User JPA Entity
    // public UserResponse mapToUserResponse(User user) {
    // return modelMapper.map(user, UserResponse.class);
    // }

}
