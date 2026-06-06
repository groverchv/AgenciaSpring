package com.AgenciaSpring.AgenciaSpring.entities;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.UUID;
import java.math.BigDecimal;
import java.time.Instant;

@DynamoDbBean
@Data
public class Oferta {
    private UUID id;
    private String titulo;
    private String descripcion;
    private String contrato;
    private String requisitos;
    private Integer experiencia_tiempo;
    private String modalidad_trabajo;
    private String nivel_educativo;
    private String estado;
    private BigDecimal sueldo;
    private Integer cluster_id;
    private Instant fecha_publicacion;
    private Instant fecha_vencimiento;
    private Categoria categoria;
    private Reclutador reclutador;

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
