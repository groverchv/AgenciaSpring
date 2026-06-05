package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad;
import com.AgenciaSpring.AgenciaSpring.services.OfertaHabilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/oferta-habilidades")
public class OfertaHabilidadRestController {

    @Autowired
    private OfertaHabilidadService service;

    @GetMapping
    public List<OfertaHabilidad> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public OfertaHabilidad getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public OfertaHabilidad create(@RequestBody OfertaHabilidad entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public OfertaHabilidad update(@PathVariable UUID id, @RequestBody OfertaHabilidad entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
