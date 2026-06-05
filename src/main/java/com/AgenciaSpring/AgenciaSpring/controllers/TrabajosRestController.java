package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Trabajos;
import com.AgenciaSpring.AgenciaSpring.services.TrabajosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trabajos")
public class TrabajosRestController {

    @Autowired
    private TrabajosService service;

    @GetMapping
    public List<Trabajos> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Trabajos getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Trabajos create(@RequestBody Trabajos entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Trabajos update(@PathVariable UUID id, @RequestBody Trabajos entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
