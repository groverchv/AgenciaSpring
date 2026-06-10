package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import com.AgenciaSpring.AgenciaSpring.repositories.UsuarioRepository;
import com.AgenciaSpring.AgenciaSpring.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Usuario> findAll() { return repository.findAll(); }
    public Optional<Usuario> findById(UUID id) { return repository.findById(id); }
    public Optional<Usuario> findByEmail(String email) { return repository.findByEmail(email); }
    public Usuario save(Usuario entity) { return repository.save(entity); }
    
    public Usuario createUserFromPython(com.AgenciaSpring.AgenciaSpring.dto.CreateUserFromPythonInput input) {
        UUID finalId = (input.getUserId() != null && !input.getUserId().isEmpty()) 
                ? UUID.fromString(input.getUserId()) 
                : UUID.randomUUID();
                
        String encodedPassword = (input.getPassword() != null) ? passwordEncoder.encode(input.getPassword()) : null;
                
        // Forzamos el insert nativo para burlar la comprobación de actualización de JPA (ahora DynamoDB)
        repository.insertUserWithId(
                finalId, 
                input.getNombre(),
                input.getApellido(),
                input.getEmail(), 
                encodedPassword, 
                input.getTelefono(),
                input.getVideo_id(), 
                "Activo"
        );
        
        // Ahora sí lo recuperamos por si se necesita
        Usuario u = new Usuario();
        u.setId(finalId);
        u.setNombre(input.getNombre());
        u.setApellido(input.getApellido());
        u.setEmail(input.getEmail());
        u.setTelefono(input.getTelefono());
        u.setVideo_id(input.getVideo_id());
        u.setEstado("Activo");

        // Buscar el rol "Candidato" dinámicamente y asignarlo
        try {
            java.util.Optional<com.AgenciaSpring.AgenciaSpring.entities.Rol> candidateRol = rolRepository.findAll().stream()
                    .filter(r -> "candidato".equalsIgnoreCase(r.getNombre()))
                    .findFirst();
            if (candidateRol.isPresent()) {
                u.setRolObj(candidateRol.get());
                repository.save(u);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar/asignar rol de candidato: " + e.getMessage());
        }

        return u;
    }
    
    public void deleteById(UUID id) { repository.deleteById(id); }
}
