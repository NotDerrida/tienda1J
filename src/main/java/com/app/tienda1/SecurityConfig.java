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
                .requestMatchers("/", "/index", "/login", "/register", "/quienesSomos", "/test").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/") // Usa la página principal (modal)
                .loginProcessingUrl("/login") // Procesa el formulario
                .defaultSuccessUrl("/") // Redirige después de iniciar sesión
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Redirige después de cerrar sesión
                .permitAll()
            );

        return http.build();
    }
}