package com.proyecto.proyecto.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired JwtUtil jwtUtil;
    @Autowired TokenBlacklistService tokenBlacklistService;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/api/observaciones",
        "/api/observaciones/",
        "/api/ubicaciones",
        "/api/ubicaciones/",
        "/api/usuarios",
        "/api/usuarios/",
        "/login",
        "/listar",
        "/",
        "/static",
        "/static/"
    );

    private boolean isPublic(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/usuarios/login") ||
            path.startsWith("/api/usuarios") ||
            path.startsWith("/api/public") ||
            path.equals("/")) {

            filterChain.doFilter(request, response);
            return;
        }
        // 🔥 1. Si la ruta es pública → ignorar COMPLETAMENTE la validación JWT
        if (isPublic(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Procesar rutas protegidas normalmente
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(jwt) || tokenBlacklistService.isBlacklisted(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.extractUsername(jwt);
        String role = (String) jwtUtil.extractAllClaims(jwt).get("rol");

        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, null, Collections.singleton(authority));

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}

