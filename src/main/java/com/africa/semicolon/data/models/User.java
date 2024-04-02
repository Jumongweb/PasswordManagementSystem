package com.africa.semicolon.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class User {
    private String firstname;
    private String lastname;
    @Id
    private String username;
    private String password;
    private List<PasswordEntry> passwordEntries;
}
