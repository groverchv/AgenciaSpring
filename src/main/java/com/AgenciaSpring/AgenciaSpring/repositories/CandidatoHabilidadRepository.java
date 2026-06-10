package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.CandidatoHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface CandidatoHabilidadRepository extends JpaRepository<CandidatoHabilidad, UUID> {
    List<CandidatoHabilidad> findByCandidatoId(UUID candidatoId);
}
