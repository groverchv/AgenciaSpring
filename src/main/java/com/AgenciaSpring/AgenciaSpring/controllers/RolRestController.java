package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Rol;
import com.AgenciaSpring.AgenciaSpring.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RolRestController {

    @Autowired
    private RolService service;

    @GetMapping
    public List<Rol> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Rol getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Rol create(@RequestBody Rol entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Rol update(@PathVariable UUID id, @RequestBody Rol entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
