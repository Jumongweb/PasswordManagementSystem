package com.africa.semicolon.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class PasswordEntry {
    @Id
    private String websiteName;
    private String username;
    private String password;
}
