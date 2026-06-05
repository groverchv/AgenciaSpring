package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Postulacion;
import com.AgenciaSpring.AgenciaSpring.repositories.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class PostulacionService {
    @Autowired
    private PostulacionRepository repository;

    public List<Postulacion> findAll() { return repository.findAll(); }
    public Optional<Postulacion> findById(UUID id) { return repository.findById(id); }
    public Postulacion save(Postulacion entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
