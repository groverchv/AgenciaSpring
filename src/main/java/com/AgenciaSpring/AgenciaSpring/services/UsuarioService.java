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

import com.AgenciaSpring.AgenciaSpring.entities.Rol;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CandidatoService candidatoService;

    public List<Usuario> findAll() { return repository.findAll(); }
    public Optional<Usuario> findById(UUID id) { return repository.findById(id); }
    public Optional<Usuario> findByEmail(String email) { return repository.findByEmail(email); }
    public Usuario save(Usuario entity) { return repository.save(entity); }
    
    public Usuario createUserFromPython(com.AgenciaSpring.AgenciaSpring.dto.CreateUserFromPythonInput input) {
        UUID finalId = (input.getUserId() != null && !input.getUserId().isEmpty()) 
                ? UUID.fromString(input.getUserId()) 
                : UUID.randomUUID();
                
        // Forzamos el insert nativo para burlar la comprobación de actualización de JPA
        repository.insertUserWithId(
                finalId, 
                input.getNombre(),
                input.getApellido(),
                input.getEmail(), 
                input.getPassword(), 
                input.getTelefono(),
                input.getVideo_id(), 
                "Activo"
        );

        // Asignar rol de Candidato
        Rol rol = rolRepository.findAll().stream()
                .filter(x -> "Candidato".equalsIgnoreCase(x.getNombre()))
                .findFirst()
                .orElse(null);
        if (rol != null) {
            Usuario u = repository.findById(finalId).orElseThrow();
            u.setRolObj(rol);
            u.setRol(rol.getNombre());
            repository.save(u);
        }

        // Insertar en la tabla candidatos
        candidatoService.insertCandidatoId(finalId);
        
        return repository.findById(finalId).orElseThrow();
    }
    
    public void deleteById(UUID id) { repository.deleteById(id); }
}
