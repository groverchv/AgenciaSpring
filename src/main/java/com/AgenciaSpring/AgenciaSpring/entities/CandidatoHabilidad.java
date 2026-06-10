package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "candidato_habilidades")
@Data
public class CandidatoHabilidad {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "habilidad_id")
    private Habilidades habilidad;
}
