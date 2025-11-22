package com.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.DTO.LoginRequest;
import com.proyecto.proyecto.DTO.LoginResponse;
import com.proyecto.proyecto.DTO.MensajeResponse;
import com.proyecto.proyecto.DTO.UsuarioUpdateRequest;
import com.proyecto.proyecto.Security.JwtUtil;
import com.proyecto.proyecto.Security.TokenBlacklistService;
import com.proyecto.proyecto.model.Usuario;
import com.proyecto.proyecto.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired IUsuarioRepository repository;
    @Autowired JwtUtil jwtUtil;
    @Autowired TokenBlacklistService tokenBlacklistService;

    @Override
    public Usuario guardar(Usuario usuario) {

        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        Optional<Usuario> existente = repository.findByCorreo(usuario.getCorreo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        usuario.setRol("usuario"); // por defecto
        usuario.setFechaRegistro(new java.util.Date());

        return repository.save(usuario);
    }

    @Override
    public MensajeResponse actualizar(String id, UsuarioUpdateRequest request) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("ID inválido");
        }
        Usuario existente = repository.findById(objectId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (request.getCorreo() == null || request.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (request.getContrasena() == null || request.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        Optional<Usuario> correoExistente = repository.findByCorreo(request.getCorreo());
        if (correoExistente.isPresent() &&
                !correoExistente.get().getId().equals(existente.getId())) {

            throw new IllegalArgumentException("El correo ya está registrado");
        }

        existente.setNombre(request.getNombre());
        existente.setCorreo(request.getCorreo());
        existente.setContrasena(request.getContrasena());

        repository.save(existente);
        return new MensajeResponse("Usuario actualizado con éxito");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String correo = request.getCorreo();
        String contrasena = request.getContrasena();

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        System.out.println("Login attempt for correo: " + request.getCorreo());
        Optional<Usuario> usuarioOpt = repository.findByCorreo(request.getCorreo());
        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuario no encontrado");
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();
        System.out.println("Encontrado usuario. Contrasena almacenada: " + usuario.getContrasena());

        // Si usas contraseñas sin hash (temporal):
        if (!request.getContrasena().equals(usuario.getContrasena())) {
            System.out.println("Contrasena NO coincide: enviado=" + request.getContrasena());
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario);

        return new LoginResponse(
            token,
            "Bearer",
            jwtUtil.getExpirationMillis(),
            usuario.getIdAsString(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getRol()
        );
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    public Usuario obtenerPorId(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public MensajeResponse eliminar(ObjectId id) {
        repository.deleteById(id);
        return new MensajeResponse("Usuario eliminado con éxito");
    }

    @Override
    public void logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklist(token);
        }
    }
}
