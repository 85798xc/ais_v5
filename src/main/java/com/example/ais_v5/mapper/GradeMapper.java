package com.example.ais_v5.mapper;

import com.example.ais_v5.dto.GradeDto;
import com.example.ais_v5.entity.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GradeMapper {
    private final UserMapper userMapper;

    public GradeDto toDto(Grade grade) {
        return new GradeDto(
                grade.getSubject(),
                grade.getGrade(),
                userMapper.toDto(grade.getUser())
        );
    }A

}
