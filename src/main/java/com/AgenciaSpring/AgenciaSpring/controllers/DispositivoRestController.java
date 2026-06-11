package com.AgenciaSpring.AgenciaSpring.controllers;

import com.AgenciaSpring.AgenciaSpring.entities.Usuario;
import com.AgenciaSpring.AgenciaSpring.services.UsuarioService;
import com.AgenciaSpring.AgenciaSpring.services.FcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoRestController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FcmService fcmService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarToken(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String userIdStr = request.get("userId");
        String fcmToken = request.get("fcmToken");

        if (fcmToken == null || fcmToken.trim().isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "fcmToken es obligatorio");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Usuario> usuarioOpt = Optional.empty();

        if (userIdStr != null && !userIdStr.trim().isEmpty()) {
            try {
                UUID userId = UUID.fromString(userIdStr);
                usuarioOpt = usuarioService.findById(userId);
            } catch (IllegalArgumentException e) {
                // Si el ID no es UUID válido, seguimos por email
            }
        }

        if (usuarioOpt.isEmpty() && email != null && !email.trim().isEmpty()) {
            usuarioOpt = usuarioService.findByEmail(email);
        }

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            boolean isNewToken = (usuario.getFcmToken() == null || !usuario.getFcmToken().equals(fcmToken));
            usuario.setFcmToken(fcmToken);
            usuarioService.save(usuario);

            // Si es candidato y es un token nuevo o primera vez, enviamos la notificacion de bienvenida
            if ("Candidato".equalsIgnoreCase(usuario.getRol())) {
                try {
                    fcmService.sendNotification(
                        fcmToken,
                        "¡Bienvenido a la Agencia de Empleo!",
                        "Hola " + usuario.getNombre() + ", gracias por registrarte. Aquí encontrarás las mejores ofertas de trabajo."
                    );
                } catch (Exception e) {
                    System.err.println("Error al enviar notificación de bienvenida: " + e.getMessage());
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Token FCM registrado exitosamente para el usuario " + usuario.getEmail());
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(404).body(response);
        }
    }
}
