package com.AgenciaSpring.AgenciaSpring.services;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import com.AgenciaSpring.AgenciaSpring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> findAll() { return repository.findAll(); }
    public Optional<Usuario> findById(UUID id) { return repository.findById(id); }
    public Optional<Usuario> findByEmail(String email) { return repository.findByEmail(email); }
    public Usuario save(Usuario entity) { return repository.save(entity); }
    
    public Usuario createUserFromPython(com.AgenciaSpring.AgenciaSpring.dto.CreateUserFromPythonInput input) {
        UUID finalId = (input.getUserId() != null && !input.getUserId().isEmpty()) 
                ? UUID.fromString(input.getUserId()) 
                : UUID.randomUUID();
                
        // Forzamos el insert nativo para burlar la comprobación de actualización de JPA (ahora DynamoDB)
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
        
        // Ahora sí lo recuperamos por si se necesita
        Usuario u = new Usuario();
        u.setId(finalId);
        u.setNombre(input.getNombre());
        u.setApellido(input.getApellido());
        u.setEmail(input.getEmail());
        u.setTelefono(input.getTelefono());
        u.setVideo_id(input.getVideo_id());
        return u;
    }
    
    public void deleteById(UUID id) { repository.deleteById(id); }
}
