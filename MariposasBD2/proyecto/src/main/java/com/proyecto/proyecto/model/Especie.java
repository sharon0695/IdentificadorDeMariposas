package com.proyecto.proyecto.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Document(collection = "especies")
public class Especie {
    @Id
    private String id;
    @Field("nombre_cientifico") 
    private String nombreCientifico;
    @Field("nombre_comun") 
    private String nombreComun;
    private String familia;
    @Field("tipo_especie") 
    private String tipoEspecie; 
    private String descripcion;
    private List<String> imagenes;

    @Field("imagenes_detalladas") 
    private ImagenesDetalladas imagenesDetalladas;
    @Field("caracteristicas_morfo") 
    private CaracteristicasMorfo caracteristicasMorfo;
    @Field("ubicacion_recoleccion") 
    private String ubicacionRecoleccion; 
    @Field("fecha_registro") 
    private Date fechaRegistro;
    @Field("registrado_por") 
    private String registradoPor; 

    public static class ImagenesDetalladas {
        @Field("ala_izquierda") 
        private String alaIzquierda;
        @Field("ala_derecha") 
        private String alaDerecha;
        private String antenas;
        private String cuerpo;
        private String patas;
        private String cabeza;
        // Getters y Setters
        public String getAlaIzquierda(){
            return alaIzquierda;
        }
        public void setAlaIzquierda(String alaIzquierda){
            this.alaIzquierda = alaIzquierda;
        }
        public String getAlaDerecha(){
            return alaDerecha;
        }
        public void setAlaDerecha(String alaDerecha){
            this.alaDerecha = alaDerecha;
        }
        public String getAntenas(){
            return antenas;
        }
        public void setAntenas(String antenas){
            this.antenas = antenas;
        }
        public String getCuerpo(){
            return cuerpo;
        }
        public void setCuerpo(String cuerpo){
            this.cuerpo = cuerpo;
        }
        public String getPatas(){
            return patas;
        }
        public void setPatas(String patas){
            this.patas = patas;
        }
        public String getCabeza(){
            return cabeza;
        }
        public void setCabeza(String cabeza){
            this.cabeza = cabeza;
        }
    }

    public static class CaracteristicasMorfo {
        private String color;
        @Field("tamano_alas") 
        private String tamanoAlas;
        @Field("forma_antenas") 
        private String formaAntenas;
        // Getters y Setters
        public String getColor(){
            return color;
        }
        public void setColor(String color){
            this.color = color;
        }
        public String getTamanoAlas(){
            return tamanoAlas;
        }
        public void setTamanoAlas(String tamañoAlas){
            this.tamanoAlas = tamañoAlas;
        }
        public String getFormaAntenas(){
            return formaAntenas;
        }
        public void setFormaAntenas(String formaAntenas){
            this.formaAntenas = formaAntenas;
        }
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
