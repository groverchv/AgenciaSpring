package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Reclutador extends Usuario {

    @Column(name = "telefono_reclutador")
    private Integer telefonoReclutador;
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}
