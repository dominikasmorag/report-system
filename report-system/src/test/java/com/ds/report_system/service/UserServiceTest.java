package com.ds.report_system.service;

import com.ds.report_system.dto.user.*;
import com.ds.report_system.entity.UserEntity;
import com.ds.report_system.exceptions.user.*;
import com.ds.report_system.repository.UserRepository;
import com.ds.report_system.security.AuthResponse;
import com.ds.report_system.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    @InjectMocks
    UserService service;

    @Test
    void shouldRegisterUser() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("andrew", "andrew123", "andrew@gmail.com");

        UserEntity savedEntity = new UserEntity();

        savedEntity.setId(1L);
        savedEntity.setUsername("andrew");
        savedEntity.setEmail("andrew@gmail.com");
        savedEntity.setRole(Role.USER);

        when(passwordEncoder.encode("andrew123"))
                .thenReturn("encodedPassword");

        when(repository.save(any(UserEntity.class)))
                .thenReturn(savedEntity);

        UserResponse result = service.register(userRegisterRequest);

        assertEquals("andrew", result.getUsername());
        assertEquals("andrew@gmail.com", result.getEmail());
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("andrew", "andrew123", "andrew@gmail.com");

        when(repository.existsByEmail("andrew@gmail.com")).thenReturn(true);

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.register(userRegisterRequest)
        );
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest("andrew", "andrew123", "andrew@gmail.com");

        when(repository.existsByUsername("andrew")).thenReturn(true);

        assertThrows(
                UsernameAlreadyExistsException.class,
                () -> service.register(userRegisterRequest)
        );
    }

    @Test
    void shouldLoginUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("andrew");
        userEntity.setEmail("andrew@gmail.com");
        userEntity.setPassword("encodedPassword");
        userEntity.setRole(Role.USER);

        UserLoginRequest userLoginRequest = new UserLoginRequest("andrew", "andrew123");

        when(repository.existsByUsername("andrew")).thenReturn(true);

        when(repository.findByUsername("andrew")).thenReturn(Optional.of(userEntity));

        when(passwordEncoder.matches("andrew123", "encodedPassword")).thenReturn(true);

        when(jwtService.generateToken(userLoginRequest.getUsername(), Role.USER)).thenReturn("fakeToken");

        when(jwtService.extractUsername("fakeToken")).thenReturn("andrew");

        when(jwtService.isTokenValid("fakeToken", "andrew")).thenReturn(true);

        AuthResponse response = service.login(userLoginRequest);

        String token = response.getToken();

        String username = jwtService.extractUsername(token);

        assertEquals("andrew", username);

        assertTrue(jwtService.isTokenValid(token, username));
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenUserNotFound() {
        UserLoginRequest request = new UserLoginRequest("andrew", "andrew123");

        when(repository.findByUsername("andrew")).thenReturn(Optional.empty());

        assertThrows(
                InvalidCredentialsException.class,
                () -> service.login(request)
        );
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenPasswordWrong() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("andrew");
        userEntity.setEmail("andrew@gmail.com");
        userEntity.setRole(Role.USER);
        userEntity.setPassword("encodedPassword");

        UserLoginRequest request = new UserLoginRequest("andrew", "andrew123");

        when(repository.findByUsername("andrew")).thenReturn(Optional.of(userEntity));

        when(passwordEncoder.matches("andrew123", "encodedPassword")).thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> service.login(request)
        );
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenIdDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.findById(1L)
        );
    }
}
