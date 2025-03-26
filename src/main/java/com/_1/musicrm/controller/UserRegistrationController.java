package com._1.musicrm.controller;

import com._1.musicrm.model.User;
import com._1.musicrm.service.UserService;
import com._1.musicrm.exception.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "注册成功");
            response.put("email", registeredUser.getEmail());
            
            return ResponseEntity.ok().body(response);
        } catch (DuplicateEmailException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }
}