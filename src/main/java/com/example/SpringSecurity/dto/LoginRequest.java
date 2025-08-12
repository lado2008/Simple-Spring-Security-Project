// dto/LoginRequest.java
package com.example.SpringSecurity.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
