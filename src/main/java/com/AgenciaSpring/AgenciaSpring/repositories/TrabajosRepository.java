package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Trabajos;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class TrabajosRepository extends DynamoDbRepository<Trabajos, UUID> {
    public TrabajosRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Trabajos", Trabajos.class);
    }
}
