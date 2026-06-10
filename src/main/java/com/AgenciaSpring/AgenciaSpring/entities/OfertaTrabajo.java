package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "oferta_trabajos")
@Data
public class OfertaTrabajo {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "trabajo_id")
    private Trabajos trabajos;
}
