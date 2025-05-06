package com.app.tienda1.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.tienda1.Models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u JOIN FETCH u.tipoUsuario")
    List<Usuario> findAllWithTipo();

    Optional<Usuario> findByEmail(String email);
}
