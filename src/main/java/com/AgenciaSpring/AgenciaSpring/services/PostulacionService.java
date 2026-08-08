package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Postulacion;
import com.AgenciaSpring.AgenciaSpring.repositories.PostulacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;

@Service
public class PostulacionService {
    @Autowired
    private PostulacionRepository repository;

    @Autowired
    private BlockchainService blockchainService;

    public List<Postulacion> findAll() { return repository.findAll(); }
    public List<Postulacion> findByReclutadorId(UUID reclutadorId) { return repository.findByOfertaReclutadorId(reclutadorId); }
    public List<Postulacion> findByCandidatoId(UUID candidatoId) { return repository.findByCandidatoId(candidatoId); }
    public Optional<Postulacion> findById(UUID id) { return repository.findById(id); }

    public Postulacion save(Postulacion entity) {
        Postulacion saved = repository.save(entity);
        
        // Log to Blockchain
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("action", "POSTULACION_GUARDADA");
            data.put("postulacionId", saved.getId());
            data.put("fecha", saved.getFecha() != null ? saved.getFecha().toString() : "");
            data.put("faseAlcanzada", saved.getFase_alcanzada());
            data.put("idCv", saved.getId_cv());
            
            if (saved.getCandidato() != null) {
                data.put("candidatoId", saved.getCandidato().getId());
            }
            if (saved.getOferta() != null) {
                data.put("ofertaId", saved.getOferta().getId());
            }

            blockchainService.registrarTransaccion("JOB_APPLICATION_" + saved.getId(), data);
        } catch (Exception e) {
            // No bloqueamos la ejecución principal si el blockchain tiene problemas
            System.err.println("Fallo al registrar postulación en Blockchain: " + e.getMessage());
        }

        return saved;
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
        
        // Log deletion to Blockchain
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("action", "POSTULACION_ELIMINADA");
            data.put("postulacionId", id);
            
            blockchainService.registrarTransaccion("JOB_APPLICATION_" + id, data);
        } catch (Exception e) {
            System.err.println("Fallo al registrar eliminación de postulación en Blockchain: " + e.getMessage());
        }
    }
}
