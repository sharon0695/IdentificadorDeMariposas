package com.proyecto.proyecto.service;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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

        Optional<Usuario> usuarioOpt = repository.findByCorreo(correo);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        Usuario usuario = usuarioOpt.get();

        if (!contrasena.equals(usuario.getContrasena())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario);

        return new LoginResponse(
            token,
            "Bearer",
            jwtUtil.getExpirationMillis(),
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
    public Usuario obtenerPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(String id) {
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
