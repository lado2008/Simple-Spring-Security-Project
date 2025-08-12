// dto/RegisterRequest.java
package com.example.SpringSecurity.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String role; // USER or ADMIN
}
