package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Usuario {
    @Id
    private UUID id;
    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;

    private String password;
    private String telefono;
    private String rol;
    private String estado;
    private String video_id;
    private Instant updated_at;
    private Instant created_at;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rolObj;
}
