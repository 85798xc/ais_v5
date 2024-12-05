package com.example.ais_v5.services;

import com.example.ais_v5.entitys.Group;
import com.example.ais_v5.entitys.Subject;
import com.example.ais_v5.entitys.User;
import com.example.ais_v5.repositorys.GroupRepository;
import com.example.ais_v5.repositorys.SubjectRepository;
import com.example.ais_v5.repositorys.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    // Create a new group
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    // Delete a group by ID
    public void deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    // Add a student to a group
    public void addStudentToGroup(Long groupId, Long studentId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if (!student.getRoles().contains("STUDENT")) {
            throw new IllegalArgumentException("User is not a student");
        }

        group.getStudents().add(student);
        groupRepository.save(group);
    }

    // Assign a subject to a group
    public void assignSubjectToGroup(Long groupId, Long subjectId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));

        group.getSubjects().add(subject);
        groupRepository.save(group);
    }
}