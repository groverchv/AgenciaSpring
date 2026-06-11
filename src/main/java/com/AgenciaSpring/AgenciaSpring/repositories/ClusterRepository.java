package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, UUID> {
    List<Cluster> findByTipo(String tipo);
    Optional<Cluster> findByTipoAndClusterNumero(String tipo, Integer clusterNumero);
    void deleteByTipo(String tipo);
}
