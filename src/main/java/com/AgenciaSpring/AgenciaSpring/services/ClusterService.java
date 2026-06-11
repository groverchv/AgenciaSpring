package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Cluster;
import com.AgenciaSpring.AgenciaSpring.repositories.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class ClusterService {
    @Autowired
    private ClusterRepository repository;

    public List<Cluster> findAll() { return repository.findAll(); }
    public Optional<Cluster> findById(UUID id) { return repository.findById(id); }
    public Cluster save(Cluster entity) { return repository.save(entity); }
    public void deleteById(UUID id) { repository.deleteById(id); }
    public List<Cluster> findByTipo(String tipo) { return repository.findByTipo(tipo); }
    public Optional<Cluster> findByTipoAndClusterNumero(String tipo, Integer clusterNumero) {
        return repository.findByTipoAndClusterNumero(tipo, clusterNumero);
    }

    @Transactional
    public void deleteByTipo(String tipo) { repository.deleteByTipo(tipo); }
}
