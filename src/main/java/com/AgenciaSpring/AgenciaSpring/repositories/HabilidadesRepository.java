package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface HabilidadesRepository extends JpaRepository<Habilidades, UUID> {
}
