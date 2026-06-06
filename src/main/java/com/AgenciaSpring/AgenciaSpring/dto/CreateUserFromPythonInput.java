package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;

@Data
public class CreateUserFromPythonInput {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String videoId;
}
