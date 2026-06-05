package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Habilidades;
import com.AgenciaSpring.AgenciaSpring.repositories.HabilidadesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class HabilidadesService {
    @Autowired
    private HabilidadesRepository repository;

    public List<Habilidades> findAll() { return repository.findAll(); }
    public Optional<Habilidades> findById(UUID id) { return repository.findById(id); }
    public Habilidades save(Habilidades entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
