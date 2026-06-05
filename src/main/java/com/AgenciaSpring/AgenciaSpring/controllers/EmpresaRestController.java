package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Empresa;
import com.AgenciaSpring.AgenciaSpring.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaRestController {

    @Autowired
    private EmpresaService service;

    @GetMapping
    public List<Empresa> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Empresa getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Empresa create(@RequestBody Empresa entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Empresa update(@PathVariable UUID id, @RequestBody Empresa entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
