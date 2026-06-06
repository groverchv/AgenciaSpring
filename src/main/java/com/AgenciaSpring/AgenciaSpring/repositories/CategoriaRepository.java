package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Categoria;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class CategoriaRepository extends DynamoDbRepository<Categoria, UUID> {
    public CategoriaRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Categoria", Categoria.class);
    }
}
