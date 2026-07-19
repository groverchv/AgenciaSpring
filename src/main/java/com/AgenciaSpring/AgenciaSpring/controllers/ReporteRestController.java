package com.AgenciaSpring.AgenciaSpring.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteRestController {

    @PersistenceContext
    private EntityManager entityManager;

    // Estructuras estáticas para la validación y esquemas
    private static final Map<String, List<String>> SCHEMA = new LinkedHashMap<>();
    private static final Map<String, String> TABLE_SOURCES = new HashMap<>();
    private static final Map<String, Map<String, String>> FIELD_MAPPINGS = new HashMap<>();

    static {
        // 1. Usuario
        SCHEMA.put("Usuario", Arrays.asList("id", "nombre", "apellido", "email", "telefono", "rol", "estado"));
        TABLE_SOURCES.put("Usuario", "usuarios");
        Map<String, String> usuarioMappings = new HashMap<>();
        for (String f : SCHEMA.get("Usuario")) {
            usuarioMappings.put(f, f);
        }
        FIELD_MAPPINGS.put("Usuario", usuarioMappings);

        // 2. Candidato
        SCHEMA.put("Candidato", Arrays.asList("id", "nombre", "apellido", "email", "telefono", "sueldo_esperado", "modalidad_preferida", "nivel_educativo", "nacionalidad", "meses_experiencia_total"));
        TABLE_SOURCES.put("Candidato", "candidatos c JOIN usuarios u ON c.id = u.id");
        Map<String, String> candidatoMappings = new HashMap<>();
        candidatoMappings.put("id", "u.id");
        candidatoMappings.put("nombre", "u.nombre");
        candidatoMappings.put("apellido", "u.apellido");
        candidatoMappings.put("email", "u.email");
        candidatoMappings.put("telefono", "u.telefono");
        candidatoMappings.put("sueldo_esperado", "c.sueldo_esperado");
        candidatoMappings.put("modalidad_preferida", "c.modalidad_preferida");
        candidatoMappings.put("nivel_educativo", "c.nivel_educativo");
        candidatoMappings.put("nacionalidad", "c.nacionalidad");
        candidatoMappings.put("meses_experiencia_total", "c.meses_experiencia_total");
        FIELD_MAPPINGS.put("Candidato", candidatoMappings);

        // 3. Empresa
        SCHEMA.put("Empresa", Arrays.asList("id", "nombre_legal", "nombre_comercial", "nit", "direccion", "celular"));
        TABLE_SOURCES.put("Empresa", "empresas");
        Map<String, String> empresaMappings = new HashMap<>();
        empresaMappings.put("id", "id");
        empresaMappings.put("nombre_legal", "nombre_legal");
        empresaMappings.put("nombre_comercial", "nombre_comercial");
        empresaMappings.put("nit", "nit");
        empresaMappings.put("direccion", "direccion");
        empresaMappings.put("celular", "celular");
        FIELD_MAPPINGS.put("Empresa", empresaMappings);

        // 4. Oferta
        SCHEMA.put("Oferta", Arrays.asList("id", "titulo", "descripcion", "contrato", "modalidad_trabajo", "nivel_educativo", "estado", "sueldo"));
        TABLE_SOURCES.put("Oferta", "ofertas");
        Map<String, String> ofertaMappings = new HashMap<>();
        for (String f : SCHEMA.get("Oferta")) {
            ofertaMappings.put(f, f);
        }
        FIELD_MAPPINGS.put("Oferta", ofertaMappings);

        // 5. Postulacion
        SCHEMA.put("Postulacion", Arrays.asList("id", "fecha", "fase_alcanzada", "candidato_nombre", "candidato_email", "oferta_titulo"));
        TABLE_SOURCES.put("Postulacion", "postulaciones p JOIN usuarios u ON p.candidato_id = u.id JOIN ofertas o ON p.oferta_id = o.id");
        Map<String, String> postulationMappings = new HashMap<>();
        postulationMappings.put("id", "p.id");
        postulationMappings.put("fecha", "p.fecha");
        postulationMappings.put("fase_alcanzada", "p.fase_alcanzada");
        postulationMappings.put("candidato_nombre", "u.nombre");
        postulationMappings.put("candidato_email", "u.email");
        postulationMappings.put("oferta_titulo", "o.titulo");
        FIELD_MAPPINGS.put("Postulacion", postulationMappings);
    }

    @GetMapping("/schema")
    public ResponseEntity<?> getSchema() {
        return ResponseEntity.ok(SCHEMA);
    }

    public static class FilterRequest {
        public String field;
        public String operator; // EQUAL, LIKE, GREATER_THAN, LESS_THAN
        public String value;
    }

    public static class ReporteRequest {
        public String table;
        public List<String> fields;
        public List<FilterRequest> filters;
    }

    @PostMapping("/ejecutar")
    public ResponseEntity<?> ejecutarReporte(@RequestBody ReporteRequest request) {
        if (request.table == null || !SCHEMA.containsKey(request.table)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Tabla no válida o no permitida"));
        }

        List<String> validFields = SCHEMA.get(request.table);
        List<String> selectFields = request.fields == null || request.fields.isEmpty() ? validFields : request.fields;

        // Validar campos
        for (String f : selectFields) {
            if (!validFields.contains(f)) {
                return ResponseEntity.badRequest().body(Map.of("error", "Campo no válido: " + f));
            }
        }

        // Construir SQL
        StringBuilder sql = new StringBuilder("SELECT ");
        Map<String, String> mappings = FIELD_MAPPINGS.get(request.table);
        
        for (int i = 0; i < selectFields.size(); i++) {
            sql.append(mappings.get(selectFields.get(i)));
            if (i < selectFields.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(" FROM ").append(TABLE_SOURCES.get(request.table));

        // Filtros
        Map<String, Object> queryParams = new HashMap<>();
        if (request.filters != null && !request.filters.isEmpty()) {
            sql.append(" WHERE ");
            for (int i = 0; i < request.filters.size(); i++) {
                FilterRequest filter = request.filters.get(i);
                if (!validFields.contains(filter.field)) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Campo de filtro no válido: " + filter.field));
                }
                
                String dbField = mappings.get(filter.field);
                String paramName = "param" + i;
                
                sql.append(dbField);
                
                switch (filter.operator.toUpperCase()) {
                    case "EQUAL":
                        sql.append(" = :").append(paramName);
                        queryParams.put(paramName, filter.value);
                        break;
                    case "LIKE":
                        sql.append(" ILIKE :").append(paramName);
                        queryParams.put(paramName, "%" + filter.value + "%");
                        break;
                    case "GREATER_THAN":
                        sql.append(" >= :").append(paramName);
                        try {
                            queryParams.put(paramName, Double.parseDouble(filter.value));
                        } catch (Exception e) {
                            queryParams.put(paramName, filter.value);
                        }
                        break;
                    case "LESS_THAN":
                        sql.append(" <= :").append(paramName);
                        try {
                            queryParams.put(paramName, Double.parseDouble(filter.value));
                        } catch (Exception e) {
                            queryParams.put(paramName, filter.value);
                        }
                        break;
                    default:
                        return ResponseEntity.badRequest().body(Map.of("error", "Operador de filtro no válido: " + filter.operator));
                }

                if (i < request.filters.size() - 1) {
                    sql.append(" AND ");
                }
            }
        }

        try {
            Query query = entityManager.createNativeQuery(sql.toString());
            for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            List<?> rawResults = query.getResultList();
            List<Map<String, Object>> formattedResults = new ArrayList<>();

            for (Object row : rawResults) {
                Map<String, Object> map = new LinkedHashMap<>();
                if (selectFields.size() == 1) {
                    map.put(selectFields.get(0), row);
                } else {
                    Object[] cells = (Object[]) row;
                    for (int i = 0; i < selectFields.size(); i++) {
                        map.put(selectFields.get(i), cells[i]);
                    }
                }
                formattedResults.add(map);
            }

            return ResponseEntity.ok(formattedResults);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Error al ejecutar la consulta: " + e.getMessage()));
        }
    }
}
