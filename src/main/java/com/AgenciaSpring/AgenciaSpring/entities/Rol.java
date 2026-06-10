package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
public class Rol {
    @Id
    private UUID id;
    private String nombre;
    private String description;
}
