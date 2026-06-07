package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.UUID;
import java.util.Optional;

@Repository
public class UsuarioRepository extends DynamoDbRepository<Usuario, UUID> {
    public UsuarioRepository(DynamoDbEnhancedClient enhancedClient) {
        super(enhancedClient, "Usuario", Usuario.class);
    }

    public Optional<Usuario> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return findAll().stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst();
    }

    public void insertUserWithId(UUID id, String nombre, String apellido, String email, String password, String telefono, String videoId, String estado) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setEmail(email);
        u.setPassword(password);
        u.setTelefono(telefono);
        u.setVideo_id(videoId);
        u.setEstado(estado);
        save(u);
    }
}
