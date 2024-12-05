package com.example.ais_v5.controllers;

import com.example.ais_v5.dto.GradeRequest;
import com.example.ais_v5.services.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lecturer")
public class LecturerController {

    private final GradeService gradeService;

    public LecturerController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    // Add or update a grade for a student
    @PostMapping("/grades")
    public ResponseEntity<String> addOrUpdateGrade(@RequestBody GradeRequest gradeRequest) {
        gradeService.addOrUpdateGrade(
                gradeRequest.getStudentId(),
                gradeRequest.getSubjectId(),
                gradeRequest.getGrade()
        );
        return ResponseEntity.ok("Grade added/updated successfully");
    }
}