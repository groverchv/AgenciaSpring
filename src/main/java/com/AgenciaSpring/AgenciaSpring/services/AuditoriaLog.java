package com.AgenciaSpring.AgenciaSpring.services;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

// Modelo o Entidad especifica para DynamoDB (ej. Historial/Auditoria)
@DynamoDbBean
public class AuditoriaLog {

    public AuditoriaLog() {
    }

    private String id;
    private String accion;
    private String detalle;
    private String fecha;

    @DynamoDbPartitionKey
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
