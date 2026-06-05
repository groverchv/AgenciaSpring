package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaTrabajo;
import com.AgenciaSpring.AgenciaSpring.repositories.OfertaTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class OfertaTrabajoService {
    @Autowired
    private OfertaTrabajoRepository repository;

    public List<OfertaTrabajo> findAll() { return repository.findAll(); }
    public Optional<OfertaTrabajo> findById(UUID id) { return repository.findById(id); }
    public OfertaTrabajo save(OfertaTrabajo entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
