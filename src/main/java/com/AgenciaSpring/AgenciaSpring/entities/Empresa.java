package com.AgenciaSpring.AgenciaSpring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "empresas")
@Data
public class Empresa {
    @Id
    private UUID id;
    private String nombre_legal;
    private String nombre_comercial;
    private Integer nit;
    private String direccion;
    private Integer celular;
    private Double latitud;
    private Double longitud;
}
