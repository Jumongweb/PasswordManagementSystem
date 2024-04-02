package com.africa.semicolon.services;

import com.africa.semicolon.dtos.Request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Test
    public void testThatUserServiceCanSaveUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        UserService.register(registerRequest);
    }
}