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
                        .requestMatchers(
                                "/",
                                "/index",
                                "/login",
                                "/logout",
                                "/register",
                                "/quienesSomos",
                                "/users",
                                "/css/**",
                                "/js/**")
                        .permitAll()
                                /*.requestMatchers("/carrito/**").authenticated()*/
                                .anyRequest().authenticated())

                        .formLogin(login -> login.disable()) 
                        .logout(logout -> logout
                        .logoutSuccessUrl("/"));
                return http.build();
        }
}
