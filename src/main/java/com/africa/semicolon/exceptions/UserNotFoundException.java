package com.africa.semicolon.exceptions;

public class UserNotFoundException extends PasswordManagementException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
