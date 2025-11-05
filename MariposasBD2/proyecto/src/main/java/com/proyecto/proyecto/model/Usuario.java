package main.java.com.proyecto.proyecto.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String correo;
    private String rol; // administrador / usuario
    private Date fechaRegistro;

    // Getters y Setters
}
