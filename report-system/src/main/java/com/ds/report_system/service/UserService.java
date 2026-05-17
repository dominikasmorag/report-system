package com.ds.report_system.service;

import com.ds.report_system.pojo.Role;
import com.ds.report_system.security.AuthResponse;
import com.ds.report_system.security.JwtService;
import com.ds.report_system.entity.UserEntity;
import com.ds.report_system.exceptions.user.EmailAlreadyExistsException;
import com.ds.report_system.exceptions.user.InvalidCredentialsException;
import com.ds.report_system.exceptions.user.UsernameAlreadyExistsException;
import com.ds.report_system.pojo.UserRequest;
import com.ds.report_system.pojo.UserResponse;
import com.ds.report_system.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(UserRequest userRequest) {
        System.out.println("UserService register started");
        UserEntity userEntity = new UserEntity();

        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already in use");
        }

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyExistsException("E-mail address is already in use");
        }
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setEmail(userRequest.getEmail());

        UserEntity saved = userRepository.save(userEntity);
        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail()
        );
    }

    public AuthResponse login(UserRequest userRequest) {
        UserEntity userEntity = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException(("Invalid credentials")));

        if(!passwordEncoder.matches(userRequest.getPassword(), userEntity.getPassword()) || !userRepository.existsByUsername(userRequest.getUsername())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateToken(userRequest.getUsername(), userEntity.getRole());

        return new AuthResponse(token);
    }

    @PostConstruct
    public void init() {
        String adminUsername = "admin";
        String adminPassword = "test";
        String adminEmail = "admin@mail.com";
        Role adminRole = Role.ADMIN;

        String userUsername = "user";
        String userPassword = "test";
        String userEmail = "user@mail.com";
        Role userRole = Role.USER;

        userRepository.save(new UserEntity(adminUsername, passwordEncoder.encode(adminPassword), adminEmail, adminRole));
        userRepository.save(new UserEntity(userUsername, passwordEncoder.encode(userPassword), userEmail, userRole));
    }
}
