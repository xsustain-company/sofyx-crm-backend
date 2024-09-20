package com.xsustain.xsustaincrm.dto;

import com.xsustain.xsustaincrm.model.Permission;

import java.util.List;

public class JwtResponse {

    private UserResponse user;
    private String jwtToken;




    public JwtResponse(UserResponse user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;

    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}