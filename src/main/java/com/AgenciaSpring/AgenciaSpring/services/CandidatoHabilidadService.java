package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.CandidatoHabilidad;
import com.AgenciaSpring.AgenciaSpring.repositories.CandidatoHabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CandidatoHabilidadService {
    @Autowired
    private CandidatoHabilidadRepository repository;

    public List<CandidatoHabilidad> findAll() { return repository.findAll(); }
    public Optional<CandidatoHabilidad> findById(UUID id) { return repository.findById(id); }
    public CandidatoHabilidad save(CandidatoHabilidad entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
