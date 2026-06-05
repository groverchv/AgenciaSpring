package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OfertaHabilidadRepository extends JpaRepository<OfertaHabilidad, UUID> {
}
