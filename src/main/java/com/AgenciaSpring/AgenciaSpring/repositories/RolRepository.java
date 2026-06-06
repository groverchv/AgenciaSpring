package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Rol;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;

@Repository
public class RolRepository extends DynamoDbRepository<Rol, UUID> {
    public RolRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Rol", Rol.class);
    }
}
