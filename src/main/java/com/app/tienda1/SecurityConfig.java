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
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso a estas rutas sin autenticación
                        .requestMatchers(
                                "/",
                                "/index",
                                "/login",
                                "/logout",
                                "/register",
                                "/quienesSomos",
                                "/test",
                                "/css/**",
                                "/js/**")
                        .permitAll()
                        // Todas las demás rutas requieren autenticación
                        .anyRequest().authenticated())
                .formLogin(login -> login.disable()) // Deshabilitamos autenticación automática
                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
        return http.build();
    }
}
