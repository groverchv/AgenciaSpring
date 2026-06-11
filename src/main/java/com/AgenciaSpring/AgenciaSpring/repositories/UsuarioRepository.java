package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.transaction.annotation.Transactional
    @org.springframework.data.jpa.repository.Query(value = "INSERT INTO usuarios (id, nombre, apellido, email, password, telefono, video_id, estado, created_at, updated_at) VALUES (:id, :nombre, :apellido, :email, :password, :telefono, :videoId, :estado, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery = true)
    void insertUserWithId(
            @org.springframework.data.repository.query.Param("id") UUID id,
            @org.springframework.data.repository.query.Param("nombre") String nombre,
            @org.springframework.data.repository.query.Param("apellido") String apellido,
            @org.springframework.data.repository.query.Param("email") String email,
            @org.springframework.data.repository.query.Param("password") String password,
            @org.springframework.data.repository.query.Param("telefono") String telefono,
            @org.springframework.data.repository.query.Param("videoId") String videoId,
            @org.springframework.data.repository.query.Param("estado") String estado
    );
}
