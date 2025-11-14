package org.iesalixar.daw2.Alejandroangulomendez.game_zone.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.CustomUserDetailsService;
import org.iesalixar.daw2.Alejandroangulomendez.game_zone.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 🔹 Excluir rutas públicas y Swagger/OpenAPI
        if (path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.startsWith("/img") ||
                path.startsWith("/uploads") ||
                path.startsWith("/api/v1/register") ||
                path.startsWith("/api/v1/authenticate")) {

            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        System.out.println("🔍 [JWT-FILTER] Authorization header recibido: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        System.out.println("🔍 [JWT-FILTER] Token extraído: " + jwt);

        final String username = jwtUtil.extractUsername(jwt);
        System.out.println("🔍 [JWT-FILTER] Usuario en token: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                Claims claims = jwtUtil.extractAllClaims(jwt);

                logger.info("Roles extraídos del token: {}", claims.get("roles"));

                List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("roles"))
                        .stream()
                        .map(Object::toString)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                logger.info("Authorities generadas: {}", authorities);
                logger.info("Usuario '{}' autenticado con roles: {}", username, authorities);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                logger.warn("Token JWT no válido para el usuario '{}'", username);
            }
        }

        filterChain.doFilter(request, response);
    }
}
