package main.java.com.proyecto.proyecto.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "observaciones")
public class Observacion {
    @Id
    private String id;
    private String especieId; // referencia a Especies
    private String usuarioId; // referencia a Usuarios
    private String comentario;
    private Date fecha;

    // Getters y Setters
}
