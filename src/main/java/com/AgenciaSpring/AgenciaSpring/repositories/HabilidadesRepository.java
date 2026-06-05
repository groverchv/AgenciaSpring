package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HabilidadesRepository extends JpaRepository<Habilidades, UUID> {
}
