package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

    default void insertUserWithId(UUID id, String nombre, String apellido, String email, String password, String telefono, String videoId, String estado) {
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
