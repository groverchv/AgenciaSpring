package com.AgenciaSpring.AgenciaSpring.repositories;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuario (id, nombre, email, password, video_id, estado) VALUES (:id, :nombre, :email, :password, :videoId, :estado)", nativeQuery = true)
    void insertUserWithId(@Param("id") UUID id, @Param("nombre") String nombre, @Param("email") String email, @Param("password") String password, @Param("videoId") String videoId, @Param("estado") String estado);
}
