package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
import com.AgenciaSpring.AgenciaSpring.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaRestController {

    @Autowired
    private OfertaService service;

    @GetMapping
    public List<Oferta> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Oferta getById(@PathVariable UUID id) { return service.findById(id).orElse(null); }

    @PostMapping
    public Oferta create(@RequestBody Oferta entity) { return service.save(entity); }

    @PutMapping("/{id}")
    public Oferta update(@PathVariable UUID id, @RequestBody Oferta entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) { service.deleteById(id); }
}
