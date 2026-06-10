package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.List;

@Repository
public interface OfertaHabilidadRepository extends JpaRepository<OfertaHabilidad, UUID> {
    List<OfertaHabilidad> findByOfertaId(UUID ofertaId);
}
