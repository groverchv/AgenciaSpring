package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;
import java.sql.Timestamp;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String rol;
    private String estado;
    private String video_id;
    private Timestamp updated_at;
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rolObj;
}
