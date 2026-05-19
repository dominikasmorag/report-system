package com.ds.report_system.service;

import com.ds.report_system.entity.UserEntity;
import com.ds.report_system.exceptions.user.EmailAlreadyExistsException;
import com.ds.report_system.exceptions.user.UsernameAlreadyExistsException;
import com.ds.report_system.pojo.Role;
import com.ds.report_system.pojo.UserRequest;
import com.ds.report_system.pojo.UserResponse;
import com.ds.report_system.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService service;

    @Test
    void shouldRegisterUser() {
        UserRequest userRequest = new UserRequest("ania", "ania123", "ania@gmail.com");

        UserEntity savedEntity = new UserEntity();

        savedEntity.setId(1L);
        savedEntity.setUsername("ania");
        savedEntity.setEmail("ania@gmail.com");

        when(passwordEncoder.encode("ania123"))
                .thenReturn("encodedPassword");

        when(repository.save(any(UserEntity.class)))
                .thenReturn(savedEntity);

        UserResponse result = service.register(userRequest);

        assertEquals("ania", result.getUsername());
        assertEquals("ania@gmail.com", result.getEmail());
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        UserRequest userRequest = new UserRequest("ania", "ania123", "ania@gmail.com");

        when(repository.existsByEmail("ania@gmail.com")).thenReturn(true);

        assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.register(userRequest)
        );
    }

    @Test
    void shouldThrowUserAlreadyExistsException() {
        UserRequest userRequest = new UserRequest("ania", "ania1234", "ania@gmail.com");

        when(repository.existsByUsername("ania")).thenReturn(true);

        assertThrows(
                UsernameAlreadyExistsException.class,
                () -> service.register(userRequest)
        );
    }
}
