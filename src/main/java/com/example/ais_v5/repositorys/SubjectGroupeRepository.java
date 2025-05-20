package com.example.ais_v5.repositorys;

import com.example.ais_v5.entity.SubjectGroupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectGroupeRepository extends JpaRepository<SubjectGroupe, Long> {
    List<SubjectGroupe> findByUsers_Username(String username);
}
