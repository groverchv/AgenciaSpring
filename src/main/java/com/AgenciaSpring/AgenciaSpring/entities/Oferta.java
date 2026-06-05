package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;
    private String descripcion;
    private String contrato;
    private String requisitos;
    private Integer experiencia_tiempo;
    private String modalidad_trabajo;
    private String estado;
    private BigDecimal sueldo;
    private Integer cluster_id;
    private Date fecha_publicacion;
    private Date fecha_vencimiento;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "reclutador_id")
    private Reclutador reclutador;
}

