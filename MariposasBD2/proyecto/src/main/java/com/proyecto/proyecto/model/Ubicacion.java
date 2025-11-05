package main.java.com.proyecto.proyecto.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ubicaciones")
public class Ubicacion {
    @Id
    private String id;
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
    }

    // Getters y Setters
}
