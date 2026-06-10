package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "reclutadores")
@PrimaryKeyJoinColumn(name = "id")
@Data
@EqualsAndHashCode(callSuper = true)
public class Reclutador extends Usuario {
    private Integer telefonoReclutador;
    private String cargo;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}
