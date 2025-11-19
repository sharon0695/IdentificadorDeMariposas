package com.proyecto.proyecto.DTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private long expiresInMillis;
    private String id;
    private String nombre;
    private String correo;
    private String rol;

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public long getExpiresInMillis() { return expiresInMillis; }
    public String getNombre() { return nombre; }
    public String getId() { return id; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public void setExpiresInMillis(long expiresInMillis) { this.expiresInMillis = expiresInMillis; }
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setRol(String rol) { this.rol = rol; }
}
