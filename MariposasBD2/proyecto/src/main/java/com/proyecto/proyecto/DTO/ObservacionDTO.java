package com.proyecto.proyecto.DTO;

import java.util.Date;

public class ObservacionDTO {

    private String id;
    private String especieId;
    private String usuarioId;
    private String comentario;
    private Date fecha;

    public ObservacionDTO(String id, String especieId, String usuarioId, String comentario, Date fecha) {
        this.id = id;
        this.especieId = especieId;
        this.usuarioId = usuarioId;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEspecieId() {
        return especieId;
    }

    public void setEspecieId(String especieId) {
        this.especieId = especieId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
