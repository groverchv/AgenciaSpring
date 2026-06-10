package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "oferta_habilidades")
@Data
public class OfertaHabilidad {
    @Id
    private UUID id;

    private String nivel_importancia;

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "habilidad_id")
    private Habilidades habilidad;
}
