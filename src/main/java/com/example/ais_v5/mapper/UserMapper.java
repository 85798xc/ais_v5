package com.example.ais_v5.mapper;

import com.example.ais_v5.dto.UserDto;
import com.example.ais_v5.entity.User;
import com.example.ais_v5.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserRepository userRepository;


    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        String groupeName = user.getGroupe() != null ? user.getGroupe().getName() : null;
        return new UserDto(
                user.getFullName(),
                groupeName
        );
    }


    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userRepository.findUserByFullNameAndGroupe(user.getFullName(),user.getGroupe()));
        user.setFullName(dto.fullName());
        return user;
    }
}
