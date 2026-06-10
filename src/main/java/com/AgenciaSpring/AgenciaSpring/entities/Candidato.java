package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Entity
@Table(name = "candidatos")
@PrimaryKeyJoinColumn(name = "id")
@Data
@EqualsAndHashCode(callSuper = true)
public class Candidato extends Usuario {
    private Integer registro;
    private BigDecimal sueldo_esperado;
    private String modalidad_preferida;
    private String nivel_educativo;
    private String nacionalidad;
    private Integer meses_experiencia_total;
    private Integer cluster_id;
}
