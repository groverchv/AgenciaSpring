package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
import com.AgenciaSpring.AgenciaSpring.repositories.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class OfertaService {
    @Autowired
    private OfertaRepository repository;

    public List<Oferta> findAll() { return repository.findAll(); }
    public Optional<Oferta> findById(UUID id) { return repository.findById(id); }
    public Oferta save(Oferta entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
