package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

// Tabla pivot: relaciona Candidato con Habilidades (muchos a muchos)
@Entity
@Table(name = "candidato_habilidad")
@Data
public class CandidatoHabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "habilidad_id")
    private Habilidades habilidad;
}
