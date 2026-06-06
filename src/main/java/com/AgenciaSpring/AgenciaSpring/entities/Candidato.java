package com.AgenciaSpring.AgenciaSpring.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import java.math.BigDecimal;

@DynamoDbBean
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
