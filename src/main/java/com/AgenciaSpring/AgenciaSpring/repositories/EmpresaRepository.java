package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Empresa;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class EmpresaRepository extends DynamoDbRepository<Empresa, UUID> {
    public EmpresaRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Empresa", Empresa.class);
    }
}
