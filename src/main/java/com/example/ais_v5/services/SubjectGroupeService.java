package com.example.ais_v5.services;

import com.example.ais_v5.entity.Subject;
import com.example.ais_v5.entity.SubjectGroupe;
import com.example.ais_v5.repositorys.SubjectGroupeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectGroupeService {
    private final SubjectGroupeRepository subjectGroupeRepository;

    @Transactional(readOnly = true)
    public List<SubjectGroupe> getAllSubjectGroupes() {
        return subjectGroupeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public SubjectGroupe getSubjectGroupeById(Long id) {
        return subjectGroupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubjectGroupe not found with id: " + id));
    }

    @Transactional
    public SubjectGroupe createSubjectGroupe(SubjectGroupe subjectGroupe) {
        return subjectGroupeRepository.save(subjectGroupe);
    }

    @Transactional
    public SubjectGroupe updateSubjectGroupe(Long id, SubjectGroupe subjectGroupeDetails) {
        SubjectGroupe subjectGroupe = subjectGroupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubjectGroupe not found with id: " + id));

        subjectGroupe.setSubject(subjectGroupeDetails.getSubject());
        subjectGroupe.setGroupe(subjectGroupeDetails.getGroupe());
        subjectGroupe.setUsers(subjectGroupeDetails.getUsers());

        return subjectGroupeRepository.save(subjectGroupe);
    }

    @Transactional
    public void deleteSubjectGroupe(Long id) {
        subjectGroupeRepository.deleteById(id);
    }

    public List<Subject> findeSubjectsByUsername(String username) {
        List<SubjectGroupe> subjectGroupes = subjectGroupeRepository.findByUsers_Username(username);
        return subjectGroupes.stream()
                .map(SubjectGroupe::getSubject)
                .collect(Collectors.toList());
    }    }
