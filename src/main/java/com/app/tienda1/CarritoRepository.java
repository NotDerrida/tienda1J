package com.app.tienda1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByClienteAndActivo(Usuario cliente, Boolean activo);
}