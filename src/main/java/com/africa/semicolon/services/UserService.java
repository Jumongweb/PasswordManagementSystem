package com.africa.semicolon.services;

import com.africa.semicolon.data.models.User;
import com.africa.semicolon.dtos.Request.LoginRequest;
import com.africa.semicolon.dtos.Request.LogoutRequest;
import com.africa.semicolon.dtos.Request.RegisterRequest;

import java.util.List;

public interface UserService {

    void register(RegisterRequest registerRequest);

    long count();

    User findUserBy(String username);

    List<User> findAllUsers();

    void deleteUserBy(String username);

    void login(LoginRequest loginRequest);



    void logout(LogoutRequest logoutRequest);
}
