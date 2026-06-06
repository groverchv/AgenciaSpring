package com.AgenciaSpring.AgenciaSpring.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
@Data
@EqualsAndHashCode(callSuper = true)
public class Reclutador extends Usuario {
    private Integer telefonoReclutador;
    private String cargo;
    private Empresa empresa;
}
