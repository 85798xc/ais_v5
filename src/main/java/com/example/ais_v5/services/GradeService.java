package com.example.ais_v5.services;

import com.example.ais_v5.entitys.Grade;
import com.example.ais_v5.entitys.Subject;
import com.example.ais_v5.entitys.User;
import com.example.ais_v5.repositorys.GradeRepository;
import com.example.ais_v5.repositorys.SubjectRepository;
import com.example.ais_v5.repositorys.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public GradeService(GradeRepository gradeRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.gradeRepository = gradeRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    // Add or update a grade for a student
    public void addOrUpdateGrade(Long studentId, Long subjectId, String gradeValue) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));

        Grade grade = gradeRepository.findByStudentAndSubject(student, subject)
                .orElse(new Grade(student, subject));
        grade.setGrade(gradeValue);

        gradeRepository.save(grade);
    }

    // Retrieve all grades for a specific student
    public List<Grade> getGradesForStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        return gradeRepository.findAllByStudent(student);
    }
}