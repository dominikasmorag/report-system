package com.ds.report_system.controller;

import com.ds.report_system.dto.user.UserRequest;
import com.ds.report_system.dto.user.UserResponse;
import com.ds.report_system.security.AuthResponse;
import com.ds.report_system.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/api/users/register")
    public UserResponse register(@RequestBody UserRequest userRequest) {
        return userService.register(userRequest);
    }

    @PostMapping(value="/api/users/login")
    public AuthResponse login(@RequestBody UserRequest userRequest) {
        System.out.println("LOGIN ENDPOINT HIT");
        return userService.login(userRequest);
    }

    @GetMapping("/api/test")
    public String test() {
        return "test ok";
    }
}
