package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "postulaciones")
@Data
public class Postulacion {
    @Id
    private UUID id;
    
    private Instant fecha;
    private String fase_alcanzada;
    private String id_cv;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;
}
