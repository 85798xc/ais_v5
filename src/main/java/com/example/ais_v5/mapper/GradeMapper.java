package com.example.ais_v5.mapper;

import com.example.ais_v5.dto.GradeDto;
import com.example.ais_v5.dto.UserDto;
import com.example.ais_v5.entity.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GradeMapper {
    private final UserMapper userMapper;

    public GradeDto toDto(Grade grade) {
        if (grade == null) {
            return null;
        }

        UserDto userDto = grade.getUser() != null ?
                userMapper.toDto(grade.getUser()) : null;

        return new GradeDto(
                grade.getSubject(),
                grade.getGrade(),
                userDto
        );
    }

    public List<GradeDto> listToDto(List<Grade> grades) {
        if (grades == null) {
            return null;
        }
        List<GradeDto> gradeDtos = new ArrayList<>();
        for (Grade grade : grades) {
            gradeDtos.add(toDto(grade));
        }
        return gradeDtos;
    }


    public Grade toEntity(GradeDto dto) {
        if (dto == null) {
            return null;
        }

        Grade grade = new Grade();
        grade.setSubject(dto.subject());
        grade.setGrade(dto.grade());
        return grade;
    }
}
