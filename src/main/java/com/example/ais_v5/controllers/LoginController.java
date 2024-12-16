package com.example.ais_v5.controllers;

import com.example.ais_v5.config.jwt.JwtProvider;
import com.example.ais_v5.dto.JwtResponse;
import com.example.ais_v5.dto.LoginRequest;
import com.example.ais_v5.dto.UserDataRequest;
import com.example.ais_v5.model.User;
import com.example.ais_v5.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService registrationService;

    public LoginController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, AuthService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        String jwt = jwtProvider.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles()));
    }


    @GetMapping("/login-error")
    public String showLoginErrorPage(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";  // This corresponds to login.html in templates or static
    }

    @PostMapping
    public String LoginPage() {
        on
    }
}