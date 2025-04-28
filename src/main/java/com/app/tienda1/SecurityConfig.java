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
                                .requestMatchers("/", "/index", "/login", "/register", "/quienesSomos", "/test").permitAll() // Permite todas estas páginas
                                .anyRequest().authenticated() // Las demás páginas requieren autenticación
                )
                .formLogin(login -> login.disable()) // Deshabilita el manejo de formulario de login de Spring Security
                .logout(logout -> logout
                                .logoutSuccessUrl("/") // Redirige después de cerrar sesión
                                .permitAll()
                );

        return http.build();
    }
}
