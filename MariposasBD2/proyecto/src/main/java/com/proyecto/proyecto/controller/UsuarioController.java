package com.proyecto.proyecto.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.DTO.LoginRequest;
import com.proyecto.proyecto.DTO.LoginResponse;
import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.DTO.RegistroRequest;
import com.proyecto.proyecto.DTO.RegistroResponse;
import com.proyecto.proyecto.DTO.UsuarioUpdateRequest;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.service.IUsuarioService;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired IUsuarioService service;

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody RegistroRequest request) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setCorreo(request.getCorreo());
            usuario.setContrasena(request.getContrasena());

            Usuario guardado = service.guardar(usuario);

            RegistroResponse response = new RegistroResponse(
                guardado.getId().toHexString(),
                guardado.getNombre(),
                guardado.getCorreo(),
                guardado.getRol(),
                "Usuario registrado exitosamente"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUsuario(
        @PathVariable String id,
        @RequestBody UsuarioUpdateRequest request) {
        MensajeResponse actualizado = service.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println(">>> Recibido: " + request);
        LoginResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        service.logout(authHeader);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable ObjectId id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public MensajeResponse eliminar(@PathVariable ObjectId id) {
        return service.eliminar(id);
    }
}
