package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Postulacion;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class PostulacionRepository extends DynamoDbRepository<Postulacion, UUID> {
    public PostulacionRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Postulacion", Postulacion.class);
    }

    public long countByCandidatoId(UUID candidatoId) {
        if (candidatoId == null) return 0;
        return findAll().stream()
                .filter(p -> p.getCandidato() != null && candidatoId.equals(p.getCandidato().getId()))
                .count();
    }
}
