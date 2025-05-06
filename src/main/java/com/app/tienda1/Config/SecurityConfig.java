package com.app.tienda1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para pruebas
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/",
                                                                "/index",
                                                                "/login",
                                                                "/logout",
                                                                "/register",
                                                                "/quienesSomos",
                                                                "/users",
                                                                "/productos",
                                                                "/images/**",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/carrito/agregar",
                                                                "/carrito/eliminar",
                                                                "/carrito/pagar",
                                                                "/carrito/pedidos/json" // Permitir acceso explícito a
                                                                                        // esta
                                                                                        // ruta
                                                ).permitAll() // Permitir acceso público a estas rutas
                                                .anyRequest().authenticated()) // Cualquier otra solicitud requiere
                                                                               // autenticación
                                .formLogin(login -> login.disable())
                                .logout(logout -> logout.logoutSuccessUrl("/"));
                return http.build();
        }
}