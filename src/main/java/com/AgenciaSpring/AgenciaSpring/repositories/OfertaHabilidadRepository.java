package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OfertaHabilidadRepository extends DynamoDbRepository<OfertaHabilidad, UUID> {
    public OfertaHabilidadRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "OfertaHabilidad", OfertaHabilidad.class);
    }

    public List<OfertaHabilidad> findByOfertaId(UUID ofertaId) {
        if (ofertaId == null) return java.util.Collections.emptyList();
        return findAll().stream()
                .filter(oh -> oh.getOferta() != null && ofertaId.equals(oh.getOferta().getId()))
                .collect(Collectors.toList());
    }
}
