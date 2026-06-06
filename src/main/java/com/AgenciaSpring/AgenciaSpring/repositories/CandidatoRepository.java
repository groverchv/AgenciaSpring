package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class CandidatoRepository extends DynamoDbRepository<Candidato, UUID> {
    public CandidatoRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Candidato", Candidato.class);
    }
}
