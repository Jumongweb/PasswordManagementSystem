package com.africa.semicolon.exceptions;

public class UsernameAlreadyExistException extends PasswordManagementException {
    public UsernameAlreadyExistException(String message) {
        super(message);
    }
}
