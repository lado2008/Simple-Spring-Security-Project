// controller/TestController.java
package com.example.SpringSecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String adminOnly() {
        return "Hello Admin!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userOnly() {
        return "Hello User!";
    }

    @GetMapping("/all")
    public String allUsers() {
        return "Hello Everyone!";
    }
}
