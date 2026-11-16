package com.losmatus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.losmatus.dto.ApiError;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Value("${cors.allowed-origins:http://localhost:3000,http://localhost:5173,http://localhost:4200}")
    private String allowedOrigins;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            ApiError error = new ApiError(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    "Unauthorized",
                                    "Acceso no autorizado: requiere autenticacion valida"
                            );
                            response.getWriter().write(objectMapper.writeValueAsString(error));
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            ApiError error = new ApiError(
                                    HttpStatus.FORBIDDEN.value(),
                                    "Forbidden",
                                    "Acceso denegado: no tiene permisos suficientes para este recurso"
                            );
                            response.getWriter().write(objectMapper.writeValueAsString(error));
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        // Preflight requests CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Endpoints publicos de autenticacion
                        .requestMatchers("/api/auth/**").permitAll()

                        // Gestion exclusiva de Administradores (recursos criticos y auditoria)
                        .requestMatchers("/api/admins/**", "/api/auditoria-informes/**", "/api/niveles-admin/**").hasRole("ADMIN")

                        // Proveedores: lectura restringida a personal interno (ADMIN y TECNICO); mutacion solo ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/proveedores/**").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers("/api/proveedores/**").hasRole("ADMIN")

                        // Operaciones administrativas de creacion/modificacion: EXCLUSIVO ADMIN
                        .requestMatchers(HttpMethod.POST,
                                "/api/categorias/**", "/api/estados-*/**", "/api/severidades/**",
                                "/api/modos-atencion/**", "/api/tipos-*/**", "/api/empresas/**",
                                "/api/empresas-detalle/**", "/api/sedes/**", "/api/areas/**",
                                "/api/tecnicos/**", "/api/modulos/**", "/api/plantillas-checklist/**",
                                "/api/productos/**", "/api/configuracion-sla/**", "/api/usuario-sede-area/**",
                                "/api/suscripciones/**", "/api/roles/**", "/api/usuarios/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/categorias/**", "/api/estados-*/**", "/api/severidades/**",
                                "/api/modos-atencion/**", "/api/tipos-*/**", "/api/empresas/**",
                                "/api/empresas-detalle/**", "/api/sedes/**", "/api/areas/**",
                                "/api/tecnicos/**", "/api/modulos/**", "/api/plantillas-checklist/**",
                                "/api/productos/**", "/api/configuracion-sla/**", "/api/usuario-sede-area/**",
                                "/api/suscripciones/**", "/api/roles/**", "/api/usuarios/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/categorias/**", "/api/estados-*/**", "/api/severidades/**",
                                "/api/modos-atencion/**", "/api/tipos-*/**", "/api/empresas/**",
                                "/api/empresas-detalle/**", "/api/sedes/**", "/api/areas/**",
                                "/api/tecnicos/**", "/api/modulos/**", "/api/plantillas-checklist/**",
                                "/api/productos/**", "/api/configuracion-sla/**", "/api/usuario-sede-area/**",
                                "/api/suscripciones/**", "/api/roles/**", "/api/usuarios/**",
                                "/api/equipos/**", "/api/atenciones/**", "/api/informes-mantenimiento/**",
                                "/api/articulos-kb/**"
                        ).hasRole("ADMIN")

                        // Operaciones de soporte tecnico interno (ADMIN y TECNICO)
                        .requestMatchers(HttpMethod.POST, "/api/equipos/**", "/api/atenciones/**", "/api/informes-mantenimiento/**", "/api/articulos-kb/**").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.PUT, "/api/equipos/**", "/api/atenciones/**", "/api/informes-mantenimiento/**", "/api/articulos-kb/**").hasAnyRole("ADMIN", "TECNICO")

                        // Costos de resolucion y cotizaciones internas: EXCLUSIVO ADMIN y TECNICO
                        .requestMatchers("/api/incidencias/*/costos/**", "/api/solicitudes-servicio/*/costos/**").hasAnyRole("ADMIN", "TECNICO")
                        .requestMatchers(HttpMethod.POST, "/api/solicitudes-servicio/*/cotizaciones/**").hasAnyRole("ADMIN", "TECNICO")

                        // Transicion manual de estados: EXCLUSIVO ADMIN y TECNICO
                        .requestMatchers(HttpMethod.PATCH, "/api/incidencias/*/estado/**", "/api/solicitudes-servicio/*/estado/**").hasAnyRole("ADMIN", "TECNICO")

                        // Cualquier otra peticion requiere autenticacion
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
