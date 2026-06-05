package com.AgenciaSpring.AgenciaSpring.services;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import java.util.UUID;

// Modelo o Entidad especifica para DynamoDB (ej. Historial/Auditoria)
@DynamoDbBean
class AuditoriaLog {
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

@Service
public class DynamoDbService {

    private final DynamoDbTable<AuditoriaLog> auditoriaTable;

    public DynamoDbService(DynamoDbEnhancedClient enhancedClient) {
        // Asume que la tabla se llama "AuditoriaLog" en AWS DynamoDB
        this.auditoriaTable = enhancedClient.table("AuditoriaLog", TableSchema.fromBean(AuditoriaLog.class));
    }

    public void crearTabla() {
        auditoriaTable.createTable();
    }

    public void guardarLog(String accion, String detalle) {
        AuditoriaLog log = new AuditoriaLog();
        log.setId(UUID.randomUUID().toString());
        log.setAccion(accion);
        log.setDetalle(detalle);
        log.setFecha(new java.util.Date().toString());
        auditoriaTable.putItem(log);
    }

    public AuditoriaLog obtenerLog(String id) {
        AuditoriaLog logQuery = new AuditoriaLog();
        logQuery.setId(id);
        return auditoriaTable.getItem(logQuery);
    }
}
