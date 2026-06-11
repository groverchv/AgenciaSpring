package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.dto.KmeansCandidateDto;
import com.AgenciaSpring.AgenciaSpring.dto.KmeansOfferDto;
import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import com.AgenciaSpring.AgenciaSpring.entities.Cluster;
import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
import com.AgenciaSpring.AgenciaSpring.entities.Postulacion;
import com.AgenciaSpring.AgenciaSpring.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
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

    @Autowired
    private ClusterService clusterService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Se ejecutará automáticamente todos los domingos a las 2:00 AM
    @Scheduled(cron = "0 0 2 * * SUN")
    public void scheduledEntrenamientoSemanal() {
        System.out.println("Iniciando entrenamiento programado semanal de K-Means...");
        entrenarCandidatosManual();
        entrenarOfertasManual();
    }

    @Transactional
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
                      clusterName
                    }
                    error
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("candidatesJson", jsonPayload)
                    .variable("nClusters", 5)
                    .retrieve("trainKmeansCandidates")
                    .toEntity(Map.class)
                    .block();

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> asignaciones = (List<Map<String, Object>>) response.get("asignaciones");
                if (asignaciones != null) {
                    // 1. Borrar clusters anteriores de tipo CANDIDATO
                    clusterService.deleteByTipo("CANDIDATO");

                    // 2. Crear los nuevos clusters
                    Map<Integer, Cluster> clusterMap = new HashMap<>();
                    for (Map<String, Object> asig : asignaciones) {
                        Integer clusterId = (Integer) asig.get("clusterId");
                        if (!clusterMap.containsKey(clusterId)) {
                            Cluster cluster = new Cluster();
                            cluster.setId(UUID.randomUUID());
                            cluster.setClusterNumero(clusterId);
                            cluster.setNombre((String) asig.get("clusterName"));
                            cluster.setTipo("CANDIDATO");
                            cluster.setFechaEntrenamiento(Instant.now());
                            clusterMap.put(clusterId, clusterService.save(cluster));
                        }
                    }

                    // 3. Asignar clusters a candidatos
                    Map<String, Candidato> candidatosMap = candidatos.stream()
                            .collect(Collectors.toMap(c -> c.getId().toString(), c -> c));

                    for (Map<String, Object> asig : asignaciones) {
                        String idStr = (String) asig.get("id");
                        Integer clusterId = (Integer) asig.get("clusterId");
                        Candidato c = candidatosMap.get(idStr);
                        if (c != null) {
                            c.setCluster(clusterMap.get(clusterId));
                        }
                    }
                    candidatoRepository.saveAll(candidatos);
                }
            }

            return "Entrenamiento de Candidatos completado: " + response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al entrenar candidatos: " + e.getMessage();
        }
    }

    @Transactional
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
                      clusterName
                    }
                    error
                  }
                }
            """;

            Map<?, ?> response = graphQlClient.document(document)
                    .variable("offersJson", jsonPayload)
                    .variable("nClusters", 5)
                    .retrieve("trainKmeansOffers")
                    .toEntity(Map.class)
                    .block();

            if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> asignaciones = (List<Map<String, Object>>) response.get("asignaciones");
                if (asignaciones != null) {
                    // 1. Borrar clusters anteriores de tipo OFERTA
                    clusterService.deleteByTipo("OFERTA");

                    // 2. Crear los nuevos clusters
                    Map<Integer, Cluster> clusterMap = new HashMap<>();
                    for (Map<String, Object> asig : asignaciones) {
                        Integer clusterId = (Integer) asig.get("clusterId");
                        if (!clusterMap.containsKey(clusterId)) {
                            Cluster cluster = new Cluster();
                            cluster.setId(UUID.randomUUID());
                            cluster.setClusterNumero(clusterId);
                            cluster.setNombre((String) asig.get("clusterName"));
                            cluster.setTipo("OFERTA");
                            cluster.setFechaEntrenamiento(Instant.now());
                            clusterMap.put(clusterId, clusterService.save(cluster));
                        }
                    }

                    // 3. Asignar clusters a ofertas
                    Map<String, Oferta> ofertasMap = ofertas.stream()
                            .collect(Collectors.toMap(o -> o.getId().toString(), o -> o));

                    for (Map<String, Object> asig : asignaciones) {
                        String idStr = (String) asig.get("id");
                        Integer clusterId = (Integer) asig.get("clusterId");
                        Oferta o = ofertasMap.get(idStr);
                        if (o != null) {
                            o.setCluster(clusterMap.get(clusterId));
                        }
                    }
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
                    clusterName
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
                Integer clusterNumero = (Integer) response.get("clusterId");
                String clusterName = (String) response.get("clusterName");

                // Buscar o crear el cluster
                Cluster cluster = clusterService.findByTipoAndClusterNumero("CANDIDATO", clusterNumero)
                        .orElseGet(() -> {
                            Cluster newCluster = new Cluster();
                            newCluster.setId(UUID.randomUUID());
                            newCluster.setClusterNumero(clusterNumero);
                            newCluster.setNombre(clusterName);
                            newCluster.setTipo("CANDIDATO");
                            newCluster.setFechaEntrenamiento(Instant.now());
                            return clusterService.save(newCluster);
                        });

                c.setCluster(cluster);
                candidatoRepository.save(c);
                return clusterNumero;
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
                    clusterName
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
                Integer clusterNumero = (Integer) response.get("clusterId");
                String clusterName = (String) response.get("clusterName");

                // Buscar o crear el cluster
                Cluster cluster = clusterService.findByTipoAndClusterNumero("OFERTA", clusterNumero)
                        .orElseGet(() -> {
                            Cluster newCluster = new Cluster();
                            newCluster.setId(UUID.randomUUID());
                            newCluster.setClusterNumero(clusterNumero);
                            newCluster.setNombre(clusterName);
                            newCluster.setTipo("OFERTA");
                            newCluster.setFechaEntrenamiento(Instant.now());
                            return clusterService.save(newCluster);
                        });

                o.setCluster(cluster);
                ofertaRepository.save(o);
                return clusterNumero;
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
        dto.setNivel_educativo_num(mapearNivelEducativo(c.getNivel_educativo()));
        // Enviar el String crudo: Python hace One-Hot Encoding internamente
        dto.setModalidad_preferida(c.getModalidad_preferida() != null ? c.getModalidad_preferida() : "Desconocido");
        dto.setTotal_postulaciones(postulacionRepository.countByCandidatoId(c.getId()));
        List<String> habilidades = candidatoHabilidadRepository.findByCandidatoId(c.getId())
                .stream()
                .map(ch -> ch.getHabilidad().getId().toString())
                .collect(Collectors.toList());
        dto.setHabilidades_ids(habilidades);
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
        // Enviar habilidades como objetos con id y peso (mapeando nivel_importancia)
        List<Map<String, Object>> habilidadesRequeridas = ofertaHabilidadRepository.findByOfertaId(o.getId())
                .stream()
                .map(oh -> {
                    Map<String, Object> habMap = new HashMap<>();
                    habMap.put("id", oh.getHabilidad().getId().toString());
                    habMap.put("peso", mapearNivelImportanciaAPeso(oh.getNivel_importancia()));
                    return habMap;
                })
                .collect(Collectors.toList());
        dto.setHabilidades_requeridas(habilidadesRequeridas);
        return dto;
    }

    private double mapearNivelImportanciaAPeso(String nivelImportancia) {
        if (nivelImportancia == null) return 1.0;
        return switch (nivelImportancia.toLowerCase()) {
            case "alta" -> 2.0;
            case "media" -> 1.5;
            case "baja" -> 1.0;
            default -> 1.0;
        };
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
