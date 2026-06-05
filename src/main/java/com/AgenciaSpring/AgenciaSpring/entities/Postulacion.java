package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.sql.Date;

@Entity
@Data
public class Postulacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date fecha;
    private String fase_alcanzada;
    private String id_cv;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "oferta_id")
    private Oferta oferta;
}

