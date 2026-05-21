package com.ds.report_system.mapper;

import com.ds.report_system.dto.user.UserResponse;
import com.ds.report_system.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserMapper() {}

    public UserResponse toDto(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getRole().name(),
                userEntity.getEmail()
        );
    }
}
