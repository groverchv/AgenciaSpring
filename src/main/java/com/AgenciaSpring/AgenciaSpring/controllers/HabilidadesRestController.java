package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Habilidades;
import com.AgenciaSpring.AgenciaSpring.services.HabilidadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/habilidades")
public class HabilidadesRestController {

    @Autowired
    private HabilidadesService service;

    @GetMapping
    public List<Habilidades> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Habilidades getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Habilidades create(@RequestBody Habilidades entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Habilidades update(@PathVariable UUID id, @RequestBody Habilidades entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
