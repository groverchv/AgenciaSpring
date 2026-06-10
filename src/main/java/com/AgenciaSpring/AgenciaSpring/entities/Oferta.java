package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "ofertas")
@Data
public class Oferta {
    @Id
    private UUID id;
    
    private String titulo;

    @Column(length = 2000)
    private String descripcion;
    
    private String contrato;

    @Column(length = 2000)
    private String requisitos;
    
    private Integer experiencia_tiempo;
    private String modalidad_trabajo;
    private String nivel_educativo;
    private String estado;
    private BigDecimal sueldo;
    private Integer cluster_id;
    private Instant fecha_publicacion;
    private Instant fecha_vencimiento;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "reclutador_id")
    private Reclutador reclutador;
}
