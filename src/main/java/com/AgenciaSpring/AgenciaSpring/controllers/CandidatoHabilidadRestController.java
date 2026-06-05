package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.CandidatoHabilidad;
import com.AgenciaSpring.AgenciaSpring.services.CandidatoHabilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidato-habilidades")
public class CandidatoHabilidadRestController {

    @Autowired
    private CandidatoHabilidadService service;

    @GetMapping
    public List<CandidatoHabilidad> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public CandidatoHabilidad getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public CandidatoHabilidad create(@RequestBody CandidatoHabilidad entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public CandidatoHabilidad update(@PathVariable UUID id, @RequestBody CandidatoHabilidad entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
