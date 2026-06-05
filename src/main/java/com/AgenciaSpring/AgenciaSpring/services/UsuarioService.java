package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import com.AgenciaSpring.AgenciaSpring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> findAll() { return repository.findAll(); }
    public Optional<Usuario> findById(UUID id) { return repository.findById(id); }
    public Usuario save(Usuario entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
}
