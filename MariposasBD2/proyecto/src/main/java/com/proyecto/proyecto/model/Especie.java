package main.java.com.proyecto.proyecto.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "especies")
public class Especie {
    @Id
    private String id;
    private String nombreCientifico;
    private String nombreComun;
    private String familia;
    private String tipoEspecie; // diurna, nocturna, etc.
    private String descripcion;
    private List<String> imagenes;

    private ImagenesDetalladas imagenesDetalladas;
    private CaracteristicasMorfo caracteristicasMorfo;
    private String ubicacionRecoleccion; // referencia a Ubicaciones
    private Date fechaRegistro;
    private String registradoPor; // referencia a Usuarios

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
}
