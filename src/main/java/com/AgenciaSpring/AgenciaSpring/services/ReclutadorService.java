package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Reclutador;
import com.AgenciaSpring.AgenciaSpring.repositories.ReclutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class ReclutadorService {
    @Autowired
    private ReclutadorRepository repository;

    public List<Reclutador> findAll() { return repository.findAll(); }
    public Optional<Reclutador> findById(UUID id) { return repository.findById(id); }
    public Reclutador save(Reclutador entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }

    public Optional<Reclutador> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return repository.findAll().stream()
                .filter(r -> email.equalsIgnoreCase(r.getEmail()))
                .findFirst();
    }
}
