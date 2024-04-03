package com.africa.semicolon.utils;

import com.africa.semicolon.data.models.User;
import com.africa.semicolon.dtos.Request.LoginRequest;
import com.africa.semicolon.dtos.Request.LogoutRequest;
import com.africa.semicolon.dtos.Request.RegisterRequest;

public class Mapper {
    public static User map(RegisterRequest registerRequest){
        User user = new User();
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        return user;
    }

    public static User map(LoginRequest loginRequest){
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        //user.setLoggedIn(true);
        return user;
    }

    public static User map(LogoutRequest logoutRequest){
        User user = new User();
        user.setUsername(logoutRequest.getUsername());
        return user;
    }

}
