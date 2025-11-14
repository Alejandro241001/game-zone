package org.iesalixar.daw2.Alejandroangulomendez.game_zone.config;

import org.iesalixar.daw2.Alejandroangulomendez.game_zone.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // === ENDPOINTS PÚBLICOS ===
                        .requestMatchers(
                                "/api/v1/authenticate",
                                "/api/v1/register",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/img/**",
                                "/uploads/**"
                        ).permitAll()

                        // === VIDEOGAMES ===
                        .requestMatchers(HttpMethod.GET, "/api/videogames/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/videogames/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/videogames/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/videogames/**").hasRole("MANAGER")

                        // === STUDIOS ===
                        .requestMatchers(HttpMethod.GET, "/api/studios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/studios/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/studios/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/studios/**").hasRole("MANAGER")

                        // === PLATFORMS ===
                        .requestMatchers(HttpMethod.GET, "/api/platforms/**").permitAll()

                        // === REVIEWS ===
                        .requestMatchers(HttpMethod.GET, "/api/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reviews/**").hasRole("NORMAL")
                        .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasRole("NORMAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("NORMAL")

                        // === USERS ===
                        .requestMatchers("/api/users/me", "/api/users/me/**").authenticated()
                        .requestMatchers("/api/users/upload-image").authenticated()
                        .requestMatchers("/api/users/change-password").authenticated()

                        // === ADMIN ===
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // === TODO LO DEMÁS REQUIERE LOGIN ===
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Entrando en el método passwordEncoder");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        logger.info("Saliendo del método passwordEncoder");
        return encoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ✅ CORS configurado correctamente para Spring Security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // <-- tu frontend Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
