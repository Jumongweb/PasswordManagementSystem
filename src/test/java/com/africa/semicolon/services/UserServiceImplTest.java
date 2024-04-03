package com.africa.semicolon.services;

import com.africa.semicolon.data.models.User;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.Request.LoginRequest;
import com.africa.semicolon.dtos.Request.LogoutRequest;
import com.africa.semicolon.dtos.Request.RegisterRequest;
import com.africa.semicolon.exceptions.InvalidPasswordException;
import com.africa.semicolon.exceptions.UserNotFoundException;
import com.africa.semicolon.exceptions.UsernameAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
    }
    @Test
    public void testThatUserServiceCanSaveUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertEquals(1, userService.count());
    }

    @Test
    public void testAdd3UserUserServiceCountIs3(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstname("firstname2");
        registerRequest2.setLastname("lastname2");
        registerRequest2.setUsername("username2");
        registerRequest2.setPassword("password2");
        userService.register(registerRequest2);
        RegisterRequest registerRequest3 = new RegisterRequest();
        registerRequest3.setFirstname("firstname3");
        registerRequest3.setLastname("lastname3");
        registerRequest3.setUsername("username3");
        registerRequest3.setPassword("password3");
        userService.register(registerRequest3);
        assertEquals(3, userService.count());
    }

    @Test
    public void testThatUserServiceCanFindUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        User foundUser = userService.findUserBy("username");
        assertEquals(foundUser.getUsername(), "username");
    }

    @Test
    public void testThatUserServiceThrowExceptionIfUserThatDoesNotExistIsSearchedFor(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThrows(UserNotFoundException.class, ()->userService.findUserBy("wrongUsername"));
    }

    @Test
    public void testThatUserServiceCannotSaveUserWithTheSameUsername(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("firstname");
        registerRequest.setLastname("lastname");
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        userService.register(registerRequest);
        assertThrows(UsernameAlreadyExistException.class, ()->userService.register(registerRequest));
        assertEquals(1, userService.count());
    }

    @Test
    public void testThatUserServiceCanFindAllUser(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstname("firstname2");
        registerRequest2.setLastname("lastname2");
        registerRequest2.setUsername("username2");
        registerRequest2.setPassword("password2");
        userService.register(registerRequest2);
        RegisterRequest registerRequest3 = new RegisterRequest();
        registerRequest3.setFirstname("firstname3");
        registerRequest3.setLastname("lastname3");
        registerRequest3.setUsername("username3");
        registerRequest3.setPassword("password3");
        userService.register(registerRequest3);
        List<User> sample = new ArrayList<>();
        sample.add(userService.findUserBy("username1"));
        sample.add(userService.findUserBy("username2"));
        sample.add(userService.findUserBy("username3"));
        assertEquals(userService.findAllUsers(), sample);
    }

    @Test
    public void testThatUserServiceCanDeleteUser(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstname("firstname2");
        registerRequest2.setLastname("lastname2");
        registerRequest2.setUsername("username2");
        registerRequest2.setPassword("password2");
        userService.register(registerRequest2);
        assertEquals(2, userService.count());
        userService.deleteUserBy("username1");
        assertEquals(1, userService.count());
    }

    @Test
    public void testDeleteUserThatDoesNotExistUserServiceThrowException(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        assertThrows(UserNotFoundException.class, ()->userService.deleteUserBy("wrongUsername"));
        assertEquals(1, userService.count());
    }

    @Test
    public void testThatUserCanLogIn(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        assertEquals(1, userService.count());
        var user = userService.findUserBy("username1");
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password1");
        userService.login(loginRequest);
        assertTrue(user.isLoggedIn());
        assertEquals(1, userService.count());
    }

    @Test
    public void testThat2UserCanLogIn(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        var user = userService.findUserBy(registerRequest1.getUsername());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password1");
        userService.login(loginRequest);
        assertTrue(user.isLoggedIn());
        // second
        RegisterRequest registerRequest2 = new RegisterRequest();
        registerRequest2.setFirstname("firstname2");
        registerRequest2.setLastname("lastname2");
        registerRequest2.setUsername("username2");
        registerRequest2.setPassword("password2");
        userService.register(registerRequest2);
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest2.setUsername("username2");
        loginRequest2.setPassword("password2");
        userService.login(loginRequest2);
        assertFalse(user.isLoggedIn());
    }


    @Test
    public void testThatUserCannotLogInWithWrongUsername(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        var user = userService.findUserBy(registerRequest1.getUsername());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("wrongUsername");
        loginRequest.setPassword("password1");
        assertThrows(UserNotFoundException.class, ()->userService.login(loginRequest));
        assertTrue(user.isLoggedIn());
    }
    @Test
    public void testThatUserCannotLogInWithWrongPassword(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        var user = userService.findUserBy(registerRequest1.getUsername());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, ()->userService.login(loginRequest));
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testThatUserCanLogout(){
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        var user = userService.findUserBy(registerRequest1.getUsername());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password1");
        userService.login(loginRequest);
        assertTrue(user.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username1");
        userService.logout(logoutRequest);
        assertFalse(user.isLoggedIn());
    }

    @Test
    public void testThatUserCanLoginLogoutAndLoginAgain(){
        userRepository.deleteAll();
        RegisterRequest registerRequest1 = new RegisterRequest();
        registerRequest1.setFirstname("firstname1");
        registerRequest1.setLastname("lastname1");
        registerRequest1.setUsername("username1");
        registerRequest1.setPassword("password1");
        userService.register(registerRequest1);
        var user = userService.findUserBy(registerRequest1.getUsername());
        assertFalse(user.isLoggedIn());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(registerRequest1.getUsername());
        loginRequest.setPassword(registerRequest1.getPassword());
        userService.login(loginRequest);
        assertTrue(user.isLoggedIn());
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username1");
        userService.logout(logoutRequest);
        assertFalse(user.isLoggedIn());
        //LoginRequest loginRequestAgain = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password1");
        userService.login(loginRequest);
        assertTrue(user.isLoggedIn());
    }


}