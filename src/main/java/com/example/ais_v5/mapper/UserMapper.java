package com.example.ais_v5.mapper;

import com.example.ais_v5.dto.UserDto;
import com.example.ais_v5.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        String groupeName = user.getGroupe() != null ? user.getGroupe().getName() : null;
        return new UserDto(
                user.getUsername(),
                user.getFullName(),
                groupeName
        );
    }

    // Note: Since UserDto is a record with limited fields,
    // we might not need a toEntity method for all use cases
    // But here's a basic implementation if needed:
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setFullName(dto.fullName());
        // Note: groupe would need to be set separately as we only have groupeName
        return user;
    }
}
