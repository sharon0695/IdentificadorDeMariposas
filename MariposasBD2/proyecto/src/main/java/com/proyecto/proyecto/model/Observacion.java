package com.proyecto.proyecto.model;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Document(collection = "observaciones")
public class Observacion {
    @Id
    private ObjectId id;
    @Field("especie_id") 
    private ObjectId especieId; 
    @Field("usuario_id")
    private ObjectId usuarioId; 
    private String comentario;
    private Date fecha;

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getEspecieId() {
        return especieId;
    }

    public void setEspecieId(ObjectId especieId) {
        this.especieId = especieId;
    }

    public ObjectId getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(ObjectId usuarioId) {
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
