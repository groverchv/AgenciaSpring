package com.AgenciaSpring.AgenciaSpring.config;

import com.AgenciaSpring.AgenciaSpring.repositories.*;
import com.AgenciaSpring.AgenciaSpring.services.DynamoDbService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DynamoDbInitializer implements CommandLineRunner {

    private final CandidatoHabilidadRepository candidatoHabilidadRepository;
    private final CandidatoRepository candidatoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EmpresaRepository empresaRepository;
    private final HabilidadesRepository habilidadesRepository;
    private final OfertaHabilidadRepository ofertaHabilidadRepository;
    private final OfertaRepository ofertaRepository;
    private final OfertaTrabajoRepository ofertaTrabajoRepository;
    private final PostulacionRepository postulacionRepository;
    private final ReclutadorRepository reclutadorRepository;
    private final RolRepository rolRepository;
    private final TrabajosRepository trabajosRepository;
    private final UsuarioRepository usuarioRepository;
    private final DynamoDbService dynamoDbService;

    public DynamoDbInitializer(
            CandidatoHabilidadRepository candidatoHabilidadRepository,
            CandidatoRepository candidatoRepository,
            CategoriaRepository categoriaRepository,
            EmpresaRepository empresaRepository,
            HabilidadesRepository habilidadesRepository,
            OfertaHabilidadRepository ofertaHabilidadRepository,
            OfertaRepository ofertaRepository,
            OfertaTrabajoRepository ofertaTrabajoRepository,
            PostulacionRepository postulacionRepository,
            ReclutadorRepository reclutadorRepository,
            RolRepository rolRepository,
            TrabajosRepository trabajosRepository,
            UsuarioRepository usuarioRepository,
            DynamoDbService dynamoDbService) {
        this.candidatoHabilidadRepository = candidatoHabilidadRepository;
        this.candidatoRepository = candidatoRepository;
        this.categoriaRepository = categoriaRepository;
        this.empresaRepository = empresaRepository;
        this.habilidadesRepository = habilidadesRepository;
        this.ofertaHabilidadRepository = ofertaHabilidadRepository;
        this.ofertaRepository = ofertaRepository;
        this.ofertaTrabajoRepository = ofertaTrabajoRepository;
        this.postulacionRepository = postulacionRepository;
        this.reclutadorRepository = reclutadorRepository;
        this.rolRepository = rolRepository;
        this.trabajosRepository = trabajosRepository;
        this.usuarioRepository = usuarioRepository;
        this.dynamoDbService = dynamoDbService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Inicializando tablas de DynamoDB...");
        
        candidatoHabilidadRepository.initTable();
        candidatoRepository.initTable();
        categoriaRepository.initTable();
        empresaRepository.initTable();
        habilidadesRepository.initTable();
        ofertaHabilidadRepository.initTable();
        ofertaRepository.initTable();
        ofertaTrabajoRepository.initTable();
        postulacionRepository.initTable();
        reclutadorRepository.initTable();
        rolRepository.initTable();
        trabajosRepository.initTable();
        usuarioRepository.initTable();
        
        try {
            dynamoDbService.crearTabla();
        } catch (Exception e) {
            // Tabla de logs ya existe
        }
        
        System.out.println("Tablas de DynamoDB inicializadas con éxito.");
    }
}
