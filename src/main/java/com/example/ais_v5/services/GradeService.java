package com.example.ais_v5.services;

import com.example.ais_v5.dto.GradeDto;
import com.example.ais_v5.entity.Grade;
import com.example.ais_v5.entity.User;
import com.example.ais_v5.mapper.GradeMapper;
import com.example.ais_v5.repositorys.GradeRepository;
import com.example.ais_v5.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final GradeMapper gradeMapper;

    public List<Grade> findGradesByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> gradeRepository.findByUserId(user.getId())).orElse(List.of());
    }

    public Grade createGrade(Grade grade, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        grade.setUser(user);
        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Long id, Grade grade, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return gradeRepository.findById(Math.toIntExact(id))
                .map(existingGrade -> {
                    if (!existingGrade.getUser().getId().equals(user.getId())) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to update this grade");
                    }
                    existingGrade.setSubject(grade.getSubject());
                    existingGrade.setGrade(grade.getGrade());
                    return gradeRepository.save(existingGrade);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found"));
    }

    public void deleteGrade(Long id, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        gradeRepository.findById(Math.toIntExact(id))
                .ifPresentOrElse(grade -> {
                    if (!grade.getUser().getId().equals(user.getId())) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete this grade");
                    }
                    gradeRepository.delete(grade);
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found");
                });
    }

    public List<Grade> findGradesBySubjectAndUsername(String subject, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return gradeRepository.findBySubjectAndUserId(subject, user.getId());
    }

    public GradeDto addGrade(GradeDto gradeDto) {
        Grade grade = gradeMapper.toEntity(gradeDto);
        Grade savedGrade = gradeRepository.save(grade);
        return gradeMapper.toDto(savedGrade);
    }

    public List<Grade> findGradesByUserId(Integer id) {
        return userRepository.findById(Long.valueOf(id)).map(user -> gradeRepository.findByUserId(user.getId())).orElse(List.of());
    }

}
