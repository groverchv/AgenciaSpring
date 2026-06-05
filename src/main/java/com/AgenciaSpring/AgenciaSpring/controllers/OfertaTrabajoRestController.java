package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaTrabajo;
import com.AgenciaSpring.AgenciaSpring.services.OfertaTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/oferta-trabajos")
public class OfertaTrabajoRestController {

    @Autowired
    private OfertaTrabajoService service;

    @GetMapping
    public List<OfertaTrabajo> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public OfertaTrabajo getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public OfertaTrabajo create(@RequestBody OfertaTrabajo entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public OfertaTrabajo update(@PathVariable UUID id, @RequestBody OfertaTrabajo entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
