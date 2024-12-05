package com.example.ais_v5.repositorys;

import com.example.ais_v5.entitys.Grade;
import com.example.ais_v5.entitys.Subject;
import com.example.ais_v5.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {


    List<Grade> findAllByStudent(User student);


    Optional<Grade> findByStudentAndSubject(User student, Subject subject);
}