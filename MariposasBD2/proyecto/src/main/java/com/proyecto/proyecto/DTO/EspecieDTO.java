package com.proyecto.proyecto.DTO;

import java.util.Date;
import java.util.List;

import com.proyecto.proyecto.model.Especie;

public class EspecieDTO {

    private String id;
    private String nombreCientifico;
    private String nombreComun;
    private String familia;
    private String tipoEspecie;
    private String descripcion;
    private List<String> imagenes;

    private ImagenesDetalladasDTO imagenesDetalladas;
    private CaracteristicasMorfoDTO caracteristicasMorfo;

    private String ubicacionRecoleccion; 
    private Date fechaRegistro;
    private String registradoPor;

    public EspecieDTO(Especie e) {
        this.id = e.getId() != null ? e.getIdAsString() : null;
        this.nombreCientifico = e.getNombreCientifico();
        this.nombreComun = e.getNombreComun();
        this.familia = e.getFamilia();
        this.tipoEspecie = e.getTipoEspecie();
        this.descripcion = e.getDescripcion();
        this.imagenes = e.getImagenes();

        // Convertir objetos anidados
        if (e.getImagenesDetalladas() != null) {
            this.imagenesDetalladas = new ImagenesDetalladasDTO(e.getImagenesDetalladas());
        }

        if (e.getCaracteristicasMorfo() != null) {
            this.caracteristicasMorfo = new CaracteristicasMorfoDTO(e.getCaracteristicasMorfo());
        }

        // Convertir objectId → String
        this.ubicacionRecoleccion = 
            e.getUbicacionRecoleccion() != null ? e.getUbicacionRecoleccionAsString() : null;

        this.fechaRegistro = e.getFechaRegistro();

        this.registradoPor = 
            e.getRegistradoPor() != null ? e.getRegistradoPorAsString() : null;
    }


    // ---------- SUB-DTOs ----------
    public static class ImagenesDetalladasDTO {
        private String alaIzquierda;
        private String alaDerecha;
        private String antenas;
        private String cuerpo;
        private String patas;
        private String cabeza;

        public ImagenesDetalladasDTO(Especie.ImagenesDetalladas img) {
            this.alaIzquierda = img.getAlaIzquierda();
            this.alaDerecha = img.getAlaDerecha();
            this.antenas = img.getAntenas();
            this.cuerpo = img.getCuerpo();
            this.patas = img.getPatas();
            this.cabeza = img.getCabeza();
        }

        // Getters
        public String getAlaIzquierda() { return alaIzquierda; }
        public String getAlaDerecha() { return alaDerecha; }
        public String getAntenas() { return antenas; }
        public String getCuerpo() { return cuerpo; }
        public String getPatas() { return patas; }
        public String getCabeza() { return cabeza; }
    }


    public static class CaracteristicasMorfoDTO {
        private String color;
        private String tamanoAlas;
        private String formaAntenas;

        public CaracteristicasMorfoDTO(Especie.CaracteristicasMorfo cm) {
            this.color = cm.getColor();
            this.tamanoAlas = cm.getTamanoAlas();
            this.formaAntenas = cm.getFormaAntenas();
        }

        public String getColor() { return color; }
        public String getTamanoAlas() { return tamanoAlas; }
        public String getFormaAntenas() { return formaAntenas; }
    }


    // ---------- GETTERS ----------
    public String getId() { return id; }
    public String getNombreCientifico() { return nombreCientifico; }
    public String getNombreComun() { return nombreComun; }
    public String getFamilia() { return familia; }
    public String getTipoEspecie() { return tipoEspecie; }
    public String getDescripcion() { return descripcion; }
    public List<String> getImagenes() { return imagenes; }
    public ImagenesDetalladasDTO getImagenesDetalladas() { return imagenesDetalladas; }
    public CaracteristicasMorfoDTO getCaracteristicasMorfo() { return caracteristicasMorfo; }
    public String getUbicacionRecoleccion() { return ubicacionRecoleccion; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public String getRegistradoPor() { return registradoPor; }
}
