package com.AgenciaSpring.AgenciaSpring.entities;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.UUID;

@DynamoDbBean
@Data
public class OfertaHabilidad {
    private UUID id;
    private String nivel_importancia;
    private Oferta oferta;
    private Habilidades habilidad;

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
