package com.AgenciaSpring.AgenciaSpring.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private boolean success;
    private String userId;
    private String message;
}
