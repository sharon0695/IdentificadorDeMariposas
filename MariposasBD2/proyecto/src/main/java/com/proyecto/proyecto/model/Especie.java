package com.proyecto.proyecto.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "especies")
public class Especie {
    @Id
    private String id;
    private String nombreCientifico;
    private String nombreComun;
    private String familia;
    private String tipoEspecie; 
    private String descripcion;
    private List<String> imagenes;

    private ImagenesDetalladas imagenesDetalladas;
    private CaracteristicasMorfo caracteristicasMorfo;
    private String ubicacionRecoleccion; 
    private Date fechaRegistro;
    private String registradoPor; 

    public static class ImagenesDetalladas {
        private String alaIzquierda;
        private String alaDerecha;
        private String antenas;
        private String cuerpo;
        private String patas;
        private String cabeza;
        // Getters y Setters
    }

    public static class CaracteristicasMorfo {
        private String color;
        private String tamañoAlas;
        private String formaAntenas;
        // Getters y Setters
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getTipoEspecie() {
        return tipoEspecie;
    }

    public void setTipoEspecie(String tipoEspecie) {
        this.tipoEspecie = tipoEspecie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public ImagenesDetalladas getImagenesDetalladas() {
        return imagenesDetalladas;
    }

    public void setImagenesDetalladas(ImagenesDetalladas imagenesDetalladas) {
        this.imagenesDetalladas = imagenesDetalladas;
    }

    public CaracteristicasMorfo getCaracteristicasMorfo() {
        return caracteristicasMorfo;
    }

    public void setCaracteristicasMorfo(CaracteristicasMorfo caracteristicasMorfo) {
        this.caracteristicasMorfo = caracteristicasMorfo;
    }

    public String getUbicacionRecoleccion() {
        return ubicacionRecoleccion;
    }

    public void setUbicacionRecoleccion(String ubicacionRecoleccion) {
        this.ubicacionRecoleccion = ubicacionRecoleccion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }
}
