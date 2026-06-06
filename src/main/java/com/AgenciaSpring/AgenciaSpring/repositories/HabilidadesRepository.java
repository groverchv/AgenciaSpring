package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Habilidades;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class HabilidadesRepository extends DynamoDbRepository<Habilidades, UUID> {
    public HabilidadesRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Habilidades", Habilidades.class);
    }
}
