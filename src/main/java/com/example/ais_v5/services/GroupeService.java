package com.example.ais_v5.services;

import com.example.ais_v5.entity.Groupe;
import com.example.ais_v5.repositorys.GroupeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupeService {
    private final GroupeRepository groupeRepository;

    @Transactional(readOnly = true)
    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Groupe getGroupeById(Long id) {
        return groupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Groupe not found with id: " + id));
    }

    @Transactional
    public Groupe createGroupe(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Transactional
    public Groupe updateGroupe(Long id, Groupe groupeDetails) {
        Groupe groupe = groupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Groupe not found with id: " + id));

        groupe.setName(groupeDetails.getName());

        return groupeRepository.save(groupe);
    }

    @Transactional
    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id);
    }
}