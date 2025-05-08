package com.example.ais_v5.services;

import com.example.ais_v5.dto.GradeDto;
import com.example.ais_v5.entity.Grade;
import com.example.ais_v5.entity.User;
import com.example.ais_v5.repositorys.GradeRepository;
import com.example.ais_v5.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;

    public List<GradeDto> findGradesByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> gradeRepository.findByUserId(user.getId()))
                .orElse(List.of());
    }

}
