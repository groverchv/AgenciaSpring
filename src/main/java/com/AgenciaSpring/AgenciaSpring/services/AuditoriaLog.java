package com.AgenciaSpring.AgenciaSpring.services;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "auditoria_logs")
public class AuditoriaLog {

    public AuditoriaLog() {
    }

    @Id
    private String id;
    private String accion;
    
    @Column(length = 2000)
    private String detalle;
    
    private String fecha;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
