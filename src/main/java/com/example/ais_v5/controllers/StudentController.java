package com.example.ais_v5.controllers;

import com.example.ais_v5.entitys.Grade;
import com.example.ais_v5.services.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final GradeService gradeService;

    public StudentController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("/{studentId}/grades")
    public ResponseEntity<List<Grade>> getGrades(@PathVariable Long studentId) {
        List<Grade> grades = gradeService.getGradesForStudent(studentId);
        return ResponseEntity.ok(grades);
    }
}