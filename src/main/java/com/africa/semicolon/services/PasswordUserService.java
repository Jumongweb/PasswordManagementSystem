package com.africa.semicolon.services;

import com.africa.semicolon.data.models.User;
import com.africa.semicolon.data.repositories.UserRepository;
import com.africa.semicolon.dtos.Request.LoginRequest;
import com.africa.semicolon.dtos.Request.LogoutRequest;
import com.africa.semicolon.dtos.Request.RegisterRequest;
import com.africa.semicolon.exceptions.InvalidPasswordException;
import com.africa.semicolon.exceptions.UserNotFoundException;
import com.africa.semicolon.exceptions.UsernameAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.africa.semicolon.utils.Mapper.map;

@Service
public class PasswordUserService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void register(RegisterRequest registerRequest) {
        validate(registerRequest.getUsername());
        User savedUser = map(registerRequest);
        userRepository.save(savedUser);
    }
    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public User findUserBy(String username) {
          User user = userRepository.findUserByUsername(username);
          if(user==null)throw  new UserNotFoundException("user not found");
          return user;
        //User foundUser = userRepository.findUserByUsername(username);
        //if (foundUser == null) throw new UserNotFoundException("User does not exist");
        //return foundUser;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserBy(String username) {
        var user = findUserBy(username);
        if (user == null) throw new UserNotFoundException(String.format("%s does not exist", username));
        userRepository.delete(user);
    }

    @Override
    public void login(LoginRequest loginRequest) {
        var user = findUserBy(loginRequest.getUsername());
       checkIfValid(loginRequest.getUsername(), loginRequest.getPassword());
       user.setLoggedIn(true);
       userRepository.save(user);
    }

    public void checkIfValid(String username, String password){
        var foundUser = findUserBy(username);
        if (!(foundUser.getUsername().equals(username))) throw new UserNotFoundException(String.format("%s does not exist", username));
        if (!(foundUser.getPassword().equals(password)))throw new InvalidPasswordException("Invalid password");
    }



    @Override
    public void logout(LogoutRequest logoutRequest) {
        User user = map(logoutRequest);
        user.setLoggedIn(true);
        userRepository.save(user);
    }

    public void validate(String username){
        for (User user : userRepository.findAll())
            if (user.getUsername().equals(username)) throw new UsernameAlreadyExistException(String.format("%s already exist", username));
    }
}
