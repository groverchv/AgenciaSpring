package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Oferta;
import com.AgenciaSpring.AgenciaSpring.repositories.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class OfertaService {
    @Autowired
    private OfertaRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FcmService fcmService;

    public List<Oferta> findAll() { return repository.findAll(); }
    public Optional<Oferta> findById(UUID id) { return repository.findById(id); }
    
    public Oferta save(Oferta entity) {
        boolean isNew = entity.getId() == null || !repository.existsById(entity.getId());
        Oferta saved = repository.save(entity);
        if (isNew) {
            try {
                List<String> tokens = usuarioService.findAll().stream()
                        .filter(u -> "Candidato".equalsIgnoreCase(u.getRol()))
                        .map(com.AgenciaSpring.AgenciaSpring.entities.Usuario::getFcmToken)
                        .filter(token -> token != null && !token.trim().isEmpty())
                        .collect(java.util.stream.Collectors.toList());
                if (!tokens.isEmpty()) {
                    String title = "Nueva Oferta de Trabajo: " + saved.getTitulo();
                    String body = saved.getDescripcion() != null ? saved.getDescripcion() : "Se ha publicado una nueva oferta de trabajo.";
                    if (body.length() > 100) {
                        body = body.substring(0, 97) + "...";
                    }
                    fcmService.sendMulticastNotification(tokens, title, body);
                }
            } catch (Exception e) {
                System.err.println("Error al enviar notificaciones de nueva oferta: " + e.getMessage());
            }
        }
        return saved;
    }

    public void deleteById(UUID id) { repository.deleteById(id); }
}
