package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RandomForestPostulacionDto {
    private BigDecimal delta_sueldo;
    private Double porcentaje_match_habilidades;
    private Integer habilidades_extra;
    private Double delta_experiencia;
    private Integer cumple_nivel_educativo;
    private Integer match_modalidad;
    private Integer es_exitosa;
}
