package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Postulacion;
import com.AgenciaSpring.AgenciaSpring.services.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/postulaciones")
public class PostulacionRestController {

    @Autowired
    private PostulacionService service;

    @GetMapping
    public List<Postulacion> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Postulacion getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Postulacion create(@RequestBody Postulacion entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Postulacion update(@PathVariable UUID id, @RequestBody Postulacion entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
