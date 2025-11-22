package com.proyecto.proyecto.DTO;

import lombok.Data;

@Data
public class UsuarioUpdateRequest {
    private String nombre;
    private String correo;
    private String contrasena;
}
