package com.example.ais_v5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login-user")
    public String showLoginPage() {
        return "login";  // This corresponds to login.html in templates or static
    }
}