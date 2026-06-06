package com.AgenciaSpring.AgenciaSpring.services;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.UUID;


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
