package com.example.ais_v5.repositorys;

import com.example.ais_v5.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByUserId(Long userId);

    List<Grade> findBySubjectAndUserId(String subject, Long id);
}

