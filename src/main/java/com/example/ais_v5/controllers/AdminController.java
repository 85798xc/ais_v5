package com.example.ais_v5.controllers;

import com.example.ais_v5.entitys.Group;
import com.example.ais_v5.entitys.Role;
import com.example.ais_v5.entitys.Subject;
import com.example.ais_v5.entitys.User;
import com.example.ais_v5.services.GroupService;
import com.example.ais_v5.services.SubjectService;
import com.example.ais_v5.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ais_v5.entitys.Role.STUDENT;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final GroupService groupService;
    private final SubjectService subjectService;

    public AdminController(UserService userService, GroupService groupService, SubjectService subjectService) {
        this.userService = userService;
        this.groupService = groupService;
        this.subjectService = subjectService;
    }

    // Create a new group
    @PostMapping("/groups")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    // Delete a group
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/students")
    public ResponseEntity<User> createStudentAccount(@RequestBody User student) {
        student.setRole(Role.STUDENT);
        student.setPassword(student.getDefaultPassword()); // Default password = lastname
        student.setEmail(student.getLogin() + "@university.com"); // Optional, default email
        User createdStudent = userService.createUser(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PostMapping("/lecturers")
    public ResponseEntity<User> createLecturerAccount(@RequestBody User lecturer) {
        lecturer.setRole(Role.LECTURER);
        lecturer.setPassword(lecturer.getDefaultPassword()); // Default password = lastname
        lecturer.setEmail(lecturer.getLogin() + "@university.com"); // Optional, default email
        User createdLecturer = userService.createUser(lecturer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLecturer);
    }


    // Delete a student account
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudentAccount(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Student account deleted successfully.");
    }

    // Delete a lecturer account
    @DeleteMapping("/lecturers/{id}")
    public ResponseEntity<String> deleteLecturerAccount(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("Lecturer account deleted successfully.");
    }

    // Create a new subject
    @PostMapping("/subjects")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject createdSubject = subjectService.createSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }

    // Assign a lecturer to a subject
    @PostMapping("/subjects/{subjectId}/assign-lecturer/{lecturerId}")
    public ResponseEntity<String> assignLecturerToSubject(
            @PathVariable Long subjectId, @PathVariable Long lecturerId) {
        subjectService.assignLecturerToSubject(subjectId, lecturerId);
        return ResponseEntity.ok("Lecturer assigned successfully");
    }

    // Add a student to a group
    @PostMapping("/groups/{groupId}/add-student/{studentId}")
    public ResponseEntity<String> addStudentToGroup(
            @PathVariable Long groupId, @PathVariable Long studentId) {
        groupService.addStudentToGroup(groupId, studentId);
        return ResponseEntity.ok("Student added to group successfully");
    }
}