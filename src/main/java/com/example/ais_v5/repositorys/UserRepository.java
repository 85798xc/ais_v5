package com.example.ais_v5.repositorys;

import com.example.ais_v5.entity.Groupe;
import com.example.ais_v5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    String findUserByFullNameAndGroupe(String fullName, Groupe groupe);

}
