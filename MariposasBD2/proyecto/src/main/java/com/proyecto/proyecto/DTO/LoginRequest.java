package com.proyecto.proyecto.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginRequest {
    private String correo;
    private String contrasena;

    public String getCorreo() { return correo; }
    public void setEmail(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setPassword(String contrasena) { this.contrasena = contrasena; }
}