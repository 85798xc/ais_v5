package com.example.ais_v5.services;

import com.example.ais_v5.entitys.Subject;
import com.example.ais_v5.entitys.User;
import com.example.ais_v5.exceptions.ResourceNotFoundException;
import com.example.ais_v5.repositorys.SubjectRepository;
import com.example.ais_v5.repositorys.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SubjectService(SubjectRepository subjectRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    public void assignLecturerToSubject(Long subjectId, Long lecturerId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        User lecturer = userRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found"));

        subject.setLecturer(lecturer);
        subjectRepository.save(subject);
    }
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Long id) {
        // Check if the subject exists before deleting
        if (!subjectRepository.existsById(id)) {
            throw new IllegalArgumentException("Subject with ID " + id + " does not exist.");
        }
        subjectRepository.deleteById(id);
    }
}
