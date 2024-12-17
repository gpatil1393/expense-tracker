package com.cd.controllers;

import com.cd.models.AuthUser;
import com.cd.services.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    private final UserService userService;

    AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JsonNode credentials) {
        AuthUser authUser = userService.validateUserCredentials(credentials.get("username").asText(), credentials.get("password").asText());
        return ResponseEntity.ok(authUser);
    }
}
