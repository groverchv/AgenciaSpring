package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad;
import com.AgenciaSpring.AgenciaSpring.repositories.OfertaHabilidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class OfertaHabilidadService {
    @Autowired
    private OfertaHabilidadRepository repository;

    public List<OfertaHabilidad> findAll() { return repository.findAll(); }
    public Optional<OfertaHabilidad> findById(UUID id) { return repository.findById(id); }
    public OfertaHabilidad save(OfertaHabilidad entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
