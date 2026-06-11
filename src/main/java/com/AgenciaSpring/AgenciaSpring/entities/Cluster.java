package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "clusters")
@Data
public class Cluster {
    @Id
    private UUID id;

    @Column(name = "cluster_numero")
    private Integer clusterNumero;

    private String nombre;

    private String tipo; // "CANDIDATO" o "OFERTA"

    @Column(name = "fecha_entrenamiento")
    private Instant fechaEntrenamiento;
}
