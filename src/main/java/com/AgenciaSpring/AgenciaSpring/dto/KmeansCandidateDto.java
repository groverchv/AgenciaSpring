package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

@Data
public class KmeansCandidateDto {
    private String id;
    private BigDecimal sueldo_esperado;
    private Integer nivel_educativo_num;        // Python espera "nivel_educativo_num"
    private String modalidad_preferida;          // Python espera el String crudo ("Remoto", "Presencial", etc.)
    private Long total_postulaciones;
    private List<String> habilidades_ids;        // Python espera "habilidades_ids"
}
