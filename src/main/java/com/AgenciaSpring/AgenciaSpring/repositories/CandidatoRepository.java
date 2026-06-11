package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, UUID> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO candidatos (id, cluster_id, meses_experiencia_total, registro) VALUES (:id, NULL, 0, 0)", nativeQuery = true)
    void insertCandidatoId(@Param("id") UUID id);
}
