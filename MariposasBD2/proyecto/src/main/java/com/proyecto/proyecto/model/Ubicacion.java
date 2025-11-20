package com.proyecto.proyecto.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Document(collection = "ubicaciones")
public class Ubicacion {
    @Id
    private ObjectId id;
    private String localidad;
    private String municipio;
    private String departamento;
    private String pais;
    private GeoLocalizacion geolocalizacion;
    private String ecosistema;

    public static class GeoLocalizacion {
        private double latitud;
        private double longitud;
        // Getters y Setters

        public double getLatitud() {
            return latitud;
        }

        public void setLatitud(double latitud) {
            this.latitud = latitud;
        }

        public double getLongitud() {
            return longitud;
        }

        public void setLongitud(double longitud) {
            this.longitud = longitud;
        }
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public GeoLocalizacion getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(GeoLocalizacion geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public String getEcosistema() {
        return ecosistema;
    }

    public void setEcosistema(String ecosistema) {
        this.ecosistema = ecosistema;
    }
}
