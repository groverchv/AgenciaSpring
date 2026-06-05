package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Reclutador;
import com.AgenciaSpring.AgenciaSpring.services.ReclutadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reclutadores")
public class ReclutadorRestController {

    @Autowired
    private ReclutadorService service;

    @GetMapping
    public List<Reclutador> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Reclutador getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Reclutador create(@RequestBody Reclutador entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Reclutador update(@PathVariable UUID id, @RequestBody Reclutador entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
