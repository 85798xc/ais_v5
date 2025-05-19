package com.example.ais_v5.controllers;

import com.example.ais_v5.entity.Grade;
import com.example.ais_v5.services.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
@RequiredArgsConstructor
@Validated
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/grades")
    public List<Grade> getUserGrades() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return gradeService.findGradesByUsername(authentication.getName());
    }

    @GetMapping("/gradesByUserId")
    public List<Grade> getUserGradesByUserId(@RequestParam int id) {
        return gradeService.findGradesByUserId(id);
    }

    @PostMapping("/createGrade")
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Grade createdGrade = gradeService.createGrade(grade, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Grade updatedGrade = gradeService.updateGrade(id, grade, authentication.getName());
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        gradeService.deleteGrade(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subject/{subject}")
    public List<Grade> getGradesBySubject(@PathVariable String subject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return gradeService.findGradesBySubjectAndUsername(subject, authentication.getName());
    }



}
