package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Categoria;
import com.AgenciaSpring.AgenciaSpring.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> findAll() { return repository.findAll(); }
    public Optional<Categoria> findById(UUID id) { return repository.findById(id); }
    public Categoria save(Categoria entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
