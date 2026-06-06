package com.AgenciaSpring.AgenciaSpring.entities;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.UUID;
import java.time.Instant;

@DynamoDbBean
@Data
public class Postulacion {
    private UUID id;
    private Instant fecha;
    private String fase_alcanzada;
    private String id_cv;
    private Candidato candidato;
    private Oferta oferta;

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
