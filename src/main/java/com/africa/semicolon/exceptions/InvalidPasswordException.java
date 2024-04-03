package com.africa.semicolon.exceptions;

public class InvalidPasswordException extends PasswordManagementException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
