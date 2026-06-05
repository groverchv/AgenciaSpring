package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Categoria;
import com.AgenciaSpring.AgenciaSpring.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<Categoria> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Categoria getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Categoria create(@RequestBody Categoria entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Categoria update(@PathVariable UUID id, @RequestBody Categoria entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
