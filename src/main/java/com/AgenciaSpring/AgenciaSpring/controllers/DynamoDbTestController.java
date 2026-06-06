package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.services.DynamoDbService;
import com.AgenciaSpring.AgenciaSpring.services.AuditoriaLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-dynamo")
public class DynamoDbTestController {

    @Autowired
    private DynamoDbService dynamoDbService;

    @PostMapping("/setup")
    public String setupTable() {
        try {
            dynamoDbService.crearTabla();
            return "Tabla 'AuditoriaLog' creada exitosamente en DynamoDB local.";
        } catch (Exception e) {
            return "Error al crear la tabla: " + e.getMessage();
        }
    }

    @PostMapping("/log")
    public String saveLog(@RequestParam String accion, @RequestParam String detalle) {
        try {
            dynamoDbService.guardarLog(accion, detalle);
            return "Log guardado exitosamente en DynamoDB local.";
        } catch (Exception e) {
            return "Error al guardar el log: " + e.getMessage();
        }
    }

    @GetMapping("/log/{id}")
    public AuditoriaLog getLog(@PathVariable String id) {
        return dynamoDbService.obtenerLog(id);
    }
}
