package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.dto.KmeansCandidateDto;
import com.AgenciaSpring.AgenciaSpring.dto.KmeansOfferDto;
import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
import com.AgenciaSpring.AgenciaSpring.entities.Postulacion;
import com.AgenciaSpring.AgenciaSpring.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MachineLearningIntegrationService {

    @Autowired
    private HttpGraphQlClient graphQlClient;

    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private CandidatoHabilidadRepository candidatoHabilidadRepository;

    @Autowired
    private OfertaHabilidadRepository ofertaHabilidadRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Se ejecutará automáticamente todos los domingos a las 2:00 AM
    @Scheduled(cron = "0 0 2 * * SUN")
    public void scheduledEntrenamientoSemanal() {
        System.out.println("Iniciando entrenamiento programado semanal de K-Means...");
        entrenarCandidatosManual();
        entrenarOfertasManual();
    }

    public String entrenarCandidatosManual() {
        try {
            List<Candidato> candidatos = candidatoRepository.findAll();
            List<KmeansCandidateDto> dtoList = candidatos.stream().map(this::mapearCandidatoADto).collect(Collectors.toList());
            String jsonPayload = objectMapper.writeValueAsString(dtoList);

            String document = """
                mutation TrainKmeansCandidates($candidatesJson: String, $nClusters: Int) {
                  trainKmeansCandidates(candidatesJson: $candidatesJson, nClusters: $nClusters) {
                    success
                    message
                    totalEntrenados
                    asignaciones {
                      id
                      clusterId
                    }
                    error
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("candidatesJson", jsonPayload)
                    .variable("nClusters", 5) // O el número que decidas
                    .retrieve("trainKmeansCandidates")
                    .toEntity(Map.class)
                    .block();

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                // Extraer asignaciones y actualizar la base de datos de manera eficiente
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> asignaciones = (List<Map<String, Object>>) response.get("asignaciones");
                if (asignaciones != null) {
                    Map<String, Candidato> candidatosMap = candidatos.stream()
                            .collect(Collectors.toMap(c -> c.getId().toString(), c -> c));

                    for (Map<String, Object> asig : asignaciones) {
                        String idStr = (String) asig.get("id");
                        Integer clusterId = (Integer) asig.get("clusterId");
                        Candidato c = candidatosMap.get(idStr);
                        if (c != null) {
                            c.setCluster_id(clusterId);
                        }
                    }
                    // Guardar todos los candidatos actualizados
                    candidatoRepository.saveAll(candidatos);
                }
            }

            return "Entrenamiento de Candidatos completado: " + response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al entrenar candidatos: " + e.getMessage();
        }
    }

    public String entrenarOfertasManual() {
        try {
            List<Oferta> ofertas = ofertaRepository.findAll();
            List<KmeansOfferDto> dtoList = ofertas.stream().map(this::mapearOfertaADto).collect(Collectors.toList());
            String jsonPayload = objectMapper.writeValueAsString(dtoList);

            String document = """
                mutation TrainKmeansOffers($offersJson: String, $nClusters: Int) {
                  trainKmeansOffers(offersJson: $offersJson, nClusters: $nClusters) {
                    success
                    message
                    totalEntrenados
                    asignaciones {
                      id
                      clusterId
                    }
                    error
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("offersJson", jsonPayload)
                    .variable("nClusters", 5) // O el número que decidas
                    .retrieve("trainKmeansOffers")
                    .toEntity(Map.class)
                    .block();

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                // Extraer asignaciones y actualizar la base de datos de manera eficiente
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> asignaciones = (List<Map<String, Object>>) response.get("asignaciones");
                if (asignaciones != null) {
                    Map<String, Oferta> ofertasMap = ofertas.stream()
                            .collect(Collectors.toMap(o -> o.getId().toString(), o -> o));

                    for (Map<String, Object> asig : asignaciones) {
                        String idStr = (String) asig.get("id");
                        Integer clusterId = (Integer) asig.get("clusterId");
                        Oferta o = ofertasMap.get(idStr);
                        if (o != null) {
                            o.setCluster_id(clusterId);
                        }
                    }
                    // Guardar todas las ofertas actualizadas
                    ofertaRepository.saveAll(ofertas);
                }
            }

            return "Entrenamiento de Ofertas completado: " + response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al entrenar ofertas: " + e.getMessage();
        }
    }

    public Integer clasificarCandidato(java.util.UUID candidatoId) {
        try {
            Candidato c = candidatoRepository.findById(candidatoId).orElseThrow(() -> new RuntimeException("Candidato no encontrado"));
            KmeansCandidateDto dto = mapearCandidatoADto(c);
            String jsonPayload = objectMapper.writeValueAsString(dto);

            String document = """
                mutation ClassifyCandidate($candidateJson: String!) {
                  classifyCandidate(candidateJson: $candidateJson) {
                    success
                    clusterId
                    message
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("candidateJson", jsonPayload)
                    .retrieve("classifyCandidate")
                    .toEntity(Map.class)
                    .block();

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                Integer clusterId = (Integer) response.get("clusterId");
                c.setCluster_id(clusterId);
                candidatoRepository.save(c);
                return clusterId;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer clasificarOferta(java.util.UUID ofertaId) {
        try {
            Oferta o = ofertaRepository.findById(ofertaId).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
            KmeansOfferDto dto = mapearOfertaADto(o);
            String jsonPayload = objectMapper.writeValueAsString(dto);

            String document = """
                mutation ClassifyOffer($offerJson: String!) {
                  classifyOffer(offerJson: $offerJson) {
                    success
                    clusterId
                    message
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("offerJson", jsonPayload)
                    .retrieve("classifyOffer")
                    .toEntity(Map.class)
                    .block();

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                Integer clusterId = (Integer) response.get("clusterId");
                o.setCluster_id(clusterId);
                ofertaRepository.save(o);
                return clusterId;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private KmeansCandidateDto mapearCandidatoADto(Candidato c) {
        KmeansCandidateDto dto = new KmeansCandidateDto();
        dto.setId(c.getId().toString());
        dto.setSueldo_esperado(c.getSueldo_esperado());
        dto.setNivel_educativo(mapearNivelEducativo(c.getNivel_educativo()));
        dto.setModalidad_preferida("Remoto".equalsIgnoreCase(c.getModalidad_preferida()) ? 1 : 0);
        dto.setTotal_postulaciones(postulacionRepository.countByCandidatoId(c.getId()));
        List<String> habilidades = candidatoHabilidadRepository.findByCandidatoId(c.getId())
                .stream()
                .map(ch -> ch.getHabilidad().getId().toString())
                .collect(Collectors.toList());
        dto.setHabilidades(habilidades);
        return dto;
    }

    private KmeansOfferDto mapearOfertaADto(Oferta o) {
        KmeansOfferDto dto = new KmeansOfferDto();
        dto.setId(o.getId().toString());
        dto.setSueldo(o.getSueldo());
        dto.setExperiencia_tiempo(o.getExperiencia_tiempo());
        dto.setModalidad_trabajo(o.getModalidad_trabajo());
        if (o.getCategoria() != null) {
            dto.setCategoria_id(o.getCategoria().getId().toString());
        }
        List<String> habilidades = ofertaHabilidadRepository.findByOfertaId(o.getId())
                .stream()
                .map(oh -> oh.getHabilidad().getId().toString())
                .collect(Collectors.toList());
        dto.setHabilidades(habilidades);
        return dto;
    }

    private Integer mapearNivelEducativo(String nivel) {
        if (nivel == null) return 0;
        return switch (nivel.toLowerCase()) {
            case "bachiller" -> 1;
            case "técnico", "tecnico" -> 2;
            case "universitario" -> 3;
            case "maestría", "maestria" -> 4;
            case "doctorado" -> 5;
            default -> 0;
        };
    }
    // ══════════════════════ RANDOM FOREST ════════════════════════════════════

    public String entrenarRandomForestManual() {
        try {
            List<Postulacion> postulaciones = postulacionRepository.findAll();
            List<com.AgenciaSpring.AgenciaSpring.dto.RandomForestPostulacionDto> dtoList = postulaciones.stream()
                    .map(this::mapearPostulacionADto)
                    .collect(Collectors.toList());
            String jsonPayload = objectMapper.writeValueAsString(dtoList);

            String document = """
                mutation TrainRandomForest($applicationsJson: String!) {
                  trainRandomForest(applicationsJson: $applicationsJson) {
                    success
                    message
                    totalEntrenados
                    error
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("applicationsJson", jsonPayload)
                    .retrieve("trainRandomForest")
                    .toEntity(Map.class)
                    .block();

            return "Entrenamiento Random Forest completado: " + response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al entrenar Random Forest: " + e.getMessage();
        }
    }

    public String predecirExitoPostulacion(java.util.UUID postulacionId) {
        try {
            Postulacion p = postulacionRepository.findById(postulacionId)
                    .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));
            com.AgenciaSpring.AgenciaSpring.dto.RandomForestPostulacionDto dto = mapearPostulacionADto(p);
            // El backend espera la forma en que FastAPI define su mutación.
            // Según tu esquema, puede ser un JSON simple, lo enviaremos como Array para mantener compatibilidad si es un array 
            // o como objecto directo. Lo enviaremos como Array de 1.
            String jsonPayload = objectMapper.writeValueAsString(List.of(dto));

            String document = """
                mutation PredictSuccess($postulationJson: String!) {
                  predictSuccess(postulationJson: $postulationJson) {
                    success
                    prediction
                    probability
                    message
                    error
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("postulationJson", jsonPayload)
                    .retrieve("predictSuccess")
                    .toEntity(Map.class)
                    .block();

            return "Predicción de Postulación: " + response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al predecir: " + e.getMessage();
        }
    }

    private com.AgenciaSpring.AgenciaSpring.dto.RandomForestPostulacionDto mapearPostulacionADto(Postulacion p) {
        com.AgenciaSpring.AgenciaSpring.dto.RandomForestPostulacionDto dto = new com.AgenciaSpring.AgenciaSpring.dto.RandomForestPostulacionDto();
        Candidato c = p.getCandidato();
        Oferta o = p.getOferta();

        // 1. Delta Sueldo (Oferta - Candidato)
        java.math.BigDecimal sueldoOferta = o.getSueldo() != null ? o.getSueldo() : java.math.BigDecimal.ZERO;
        java.math.BigDecimal sueldoCandidato = c.getSueldo_esperado() != null ? c.getSueldo_esperado() : java.math.BigDecimal.ZERO;
        dto.setDelta_sueldo(sueldoOferta.subtract(sueldoCandidato));

        // 2. Habilidades
        List<com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad> ofertaHabilidades = ofertaHabilidadRepository.findByOfertaId(o.getId());
        List<com.AgenciaSpring.AgenciaSpring.entities.CandidatoHabilidad> candidatoHabilidades = candidatoHabilidadRepository.findByCandidatoId(c.getId());
        
        long coincidencias = 0;
        for (com.AgenciaSpring.AgenciaSpring.entities.OfertaHabilidad oh : ofertaHabilidades) {
            boolean candidatoLaTiene = candidatoHabilidades.stream()
                    .anyMatch(ch -> ch.getHabilidad().getId().equals(oh.getHabilidad().getId()));
            if (candidatoLaTiene) coincidencias++;
        }
        
        double porcentajeMatch = ofertaHabilidades.isEmpty() ? 100.0 : ((double) coincidencias / ofertaHabilidades.size()) * 100.0;
        dto.setPorcentaje_match_habilidades(porcentajeMatch);

        int habilidadesExtra = (int) (candidatoHabilidades.size() - coincidencias);
        dto.setHabilidades_extra(habilidadesExtra > 0 ? habilidadesExtra : 0);

        // 3. Experiencia (Años de experiencia candidato - años requeridos)
        double expCandidatoAnios = c.getMeses_experiencia_total() != null ? c.getMeses_experiencia_total() / 12.0 : 0.0;
        double expOfertaAnios = o.getExperiencia_tiempo() != null ? o.getExperiencia_tiempo() : 0.0;
        dto.setDelta_experiencia(expCandidatoAnios - expOfertaAnios);

        // 4. Nivel Educativo
        int nivelCandidato = mapearNivelEducativo(c.getNivel_educativo());
        int nivelOferta = mapearNivelEducativo(o.getNivel_educativo());
        dto.setCumple_nivel_educativo(nivelCandidato >= nivelOferta ? 1 : 0);

        // 5. Modalidad
        boolean matchModalidad = c.getModalidad_preferida() != null && 
                                 c.getModalidad_preferida().equalsIgnoreCase(o.getModalidad_trabajo());
        dto.setMatch_modalidad(matchModalidad ? 1 : 0);

        // 6. Es Exitosa
        String fase = p.getFase_alcanzada();
        int esExitosa = 0;
        if (fase != null && (fase.equalsIgnoreCase("Contratado") || 
                             fase.equalsIgnoreCase("Oferta Realizada") || 
                             fase.equalsIgnoreCase("Aprobó Entrevista Técnica"))) {
            esExitosa = 1;
        }
        dto.setEs_exitosa(esExitosa);

        return dto;
    }
}
