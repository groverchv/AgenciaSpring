package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.dto.KmeansCandidateDto;
import com.AgenciaSpring.AgenciaSpring.dto.KmeansOfferDto;
import com.AgenciaSpring.AgenciaSpring.entities.Candidato;
import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
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
}
