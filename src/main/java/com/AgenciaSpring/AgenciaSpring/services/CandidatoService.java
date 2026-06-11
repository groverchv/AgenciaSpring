package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import com.AgenciaSpring.AgenciaSpring.repositories.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CandidatoService {
    @Autowired
    private CandidatoRepository repository;

    public List<Candidato> findAll() { return repository.findAll(); }
    public Optional<Candidato> findById(UUID id) { return repository.findById(id); }
    public Candidato save(Candidato entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }

    public Optional<Candidato> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return repository.findAll().stream()
                .filter(c -> email.equalsIgnoreCase(c.getEmail()))
                .findFirst();
    }

    public void insertCandidatoId(UUID id) {
        repository.insertCandidatoId(id);
    }
}
