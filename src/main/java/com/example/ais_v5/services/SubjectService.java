package com.example.ais_v5.services;

import com.example.ais_v5.entity.Subject;
import com.example.ais_v5.repositorys.SubjectRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    @Transactional
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject updateSubject(Long id, Subject subjectDetails) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));

        subject.setName(subjectDetails.getName());
        subject.setCode(subjectDetails.getCode());

        return subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}