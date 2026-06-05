package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Empresa;
import com.AgenciaSpring.AgenciaSpring.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository repository;

    public List<Empresa> findAll() { return repository.findAll(); }
    public Optional<Empresa> findById(UUID id) { return repository.findById(id); }
    public Empresa save(Empresa entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
