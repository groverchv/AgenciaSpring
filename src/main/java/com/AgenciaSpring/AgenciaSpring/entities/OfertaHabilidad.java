package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

// Tabla pivot: relaciona Oferta con Habilidades (muchos a muchos) con nivel de importancia
@Entity
@Table(name = "oferta_habilidad")
@Data
public class OfertaHabilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nivel_importancia;

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "habilidad_id")
    private Habilidades habilidad;
}
