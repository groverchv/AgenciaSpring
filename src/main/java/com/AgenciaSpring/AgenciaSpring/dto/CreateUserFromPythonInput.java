package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;

@Data
public class CreateUserFromPythonInput {
    private String userId;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String video_id;
}
