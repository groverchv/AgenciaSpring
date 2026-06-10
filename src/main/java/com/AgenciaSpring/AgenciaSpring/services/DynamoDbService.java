package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.repositories.AuditoriaLogRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class DynamoDbService {

    private final AuditoriaLogRepository repository;

    public DynamoDbService(AuditoriaLogRepository repository) {
        this.repository = repository;
    }

    public void crearTabla() {
        // No-op: JPA auto-creates tables via spring.jpa.hibernate.ddl-auto=update
    }

    public void guardarLog(String accion, String detalle) {
        AuditoriaLog log = new AuditoriaLog();
        log.setId(UUID.randomUUID().toString());
        log.setAccion(accion);
        log.setDetalle(detalle);
        log.setFecha(new java.util.Date().toString());
        repository.save(log);
    }

    public AuditoriaLog obtenerLog(String id) {
        return repository.findById(id).orElse(null);
    }
}
