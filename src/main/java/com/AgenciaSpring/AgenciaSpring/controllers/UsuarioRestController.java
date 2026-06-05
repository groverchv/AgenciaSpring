package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import com.AgenciaSpring.AgenciaSpring.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Usuario getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Usuario create(@RequestBody Usuario entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable UUID id, @RequestBody Usuario entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
