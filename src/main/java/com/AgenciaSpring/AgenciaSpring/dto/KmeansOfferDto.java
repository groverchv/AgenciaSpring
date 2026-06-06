package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Data
public class KmeansOfferDto {
    private String id;
    private BigDecimal sueldo;
    private Integer experiencia_tiempo;
    private String modalidad_trabajo;
    private String categoria_id;
    private List<String> habilidades;
}
