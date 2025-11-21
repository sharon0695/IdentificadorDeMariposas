package com.proyecto.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.DTO.LoginRequest;
import com.proyecto.proyecto.DTO.LoginResponse;
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

    // 1. Validación de campos obligatorios
    if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
        throw new IllegalArgumentException("El nombre es obligatorio");
    }
    if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
        throw new IllegalArgumentException("El correo es obligatorio");
    }
    if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
        throw new IllegalArgumentException("La contraseña es obligatoria");
    }

    // 2. Verificar si el correo ya existe
    Optional<Usuario> existente = repository.findByCorreo(usuario.getCorreo());
    if (existente.isPresent()) {
        throw new IllegalArgumentException("El correo ya está registrado");
    }

    // 3. Asignar valores por defecto
    usuario.setRol("usuario"); // por defecto
    usuario.setFechaRegistro(new java.util.Date());

    // 4. Guardar en Mongo
    return repository.save(usuario);
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
            usuario.getId().toHexString(),
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
    public void eliminar(ObjectId id) {
        repository.deleteById(id);
    }

    @Override
    public void logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklist(token);
        }
    }
}
