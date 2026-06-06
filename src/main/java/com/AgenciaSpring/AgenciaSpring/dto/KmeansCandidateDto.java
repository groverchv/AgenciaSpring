package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

@Data
public class KmeansCandidateDto {
    private String id;
    private BigDecimal sueldo_esperado;
    private Integer nivel_educativo;
    private Integer modalidad_preferida; // 1 Remoto, 0 Presencial, etc.
    private Long total_postulaciones;
    private List<String> habilidades;
}
