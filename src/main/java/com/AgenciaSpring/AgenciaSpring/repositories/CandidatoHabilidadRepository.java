package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.CandidatoHabilidad;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CandidatoHabilidadRepository extends DynamoDbRepository<CandidatoHabilidad, UUID> {
    public CandidatoHabilidadRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "CandidatoHabilidad", CandidatoHabilidad.class);
    }

    public List<CandidatoHabilidad> findByCandidatoId(UUID candidatoId) {
        if (candidatoId == null) return java.util.Collections.emptyList();
        return findAll().stream()
                .filter(ch -> ch.getCandidato() != null && candidatoId.equals(ch.getCandidato().getId()))
                .collect(Collectors.toList());
    }
}
