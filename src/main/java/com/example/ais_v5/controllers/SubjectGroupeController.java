package com.example.ais_v5.controllers;

import com.example.ais_v5.entity.SubjectGroupe;
import com.example.ais_v5.services.SubjectGroupeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subject-groupes")
@RequiredArgsConstructor
public class SubjectGroupeController {
    private final SubjectGroupeService subjectGroupeService;

    @GetMapping
    public List<SubjectGroupe> getAllSubjectGroupes() {
        return subjectGroupeService.getAllSubjectGroupes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectGroupe> getSubjectGroupeById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectGroupeService.getSubjectGroupeById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectGroupe> createSubjectGroupe(@RequestBody SubjectGroupe subjectGroupe) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectGroupeService.createSubjectGroupe(subjectGroupe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectGroupe> updateSubjectGroupe(@PathVariable Long id, @RequestBody SubjectGroupe subjectGroupe) {
        return ResponseEntity.ok(subjectGroupeService.updateSubjectGroupe(id, subjectGroupe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubjectGroupe(@PathVariable Long id) {
        subjectGroupeService.deleteSubjectGroupe(id);
        return ResponseEntity.noContent().build();
    }
}