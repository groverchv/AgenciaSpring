package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Trabajos;
import com.AgenciaSpring.AgenciaSpring.repositories.TrabajosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class TrabajosService {
    @Autowired
    private TrabajosRepository repository;

    public List<Trabajos> findAll() { return repository.findAll(); }
    public Optional<Trabajos> findById(UUID id) { return repository.findById(id); }
    public Trabajos save(Trabajos entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
