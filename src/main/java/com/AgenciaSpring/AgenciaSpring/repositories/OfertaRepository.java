package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class OfertaRepository extends DynamoDbRepository<Oferta, UUID> {
    public OfertaRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Oferta", Oferta.class);
    }
}
