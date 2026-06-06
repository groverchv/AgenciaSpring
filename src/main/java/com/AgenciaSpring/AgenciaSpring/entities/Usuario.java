package com.AgenciaSpring.AgenciaSpring.entities;

import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.UUID;
import java.time.Instant;

@DynamoDbBean
@Data
public class Usuario {
    private UUID id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String rol;
    private String estado;
    private String video_id;
    private Instant updated_at;
    private Instant created_at;
    private Rol rolObj;

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
