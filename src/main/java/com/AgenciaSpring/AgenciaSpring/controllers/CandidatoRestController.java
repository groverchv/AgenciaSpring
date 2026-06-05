package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import com.AgenciaSpring.AgenciaSpring.services.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoRestController {

    @Autowired
    private CandidatoService service;

    @GetMapping
    public List<Candidato> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Candidato getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Candidato create(@RequestBody Candidato entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Candidato update(@PathVariable UUID id, @RequestBody Candidato entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
