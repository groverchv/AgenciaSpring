package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Reclutador;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class ReclutadorRepository extends DynamoDbRepository<Reclutador, UUID> {
    public ReclutadorRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Reclutador", Reclutador.class);
    }
}
