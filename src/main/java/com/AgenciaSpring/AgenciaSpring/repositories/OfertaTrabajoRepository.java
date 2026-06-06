package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaTrabajo;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class OfertaTrabajoRepository extends DynamoDbRepository<OfertaTrabajo, UUID> {
    public OfertaTrabajoRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "OfertaTrabajo", OfertaTrabajo.class);
    }
}
