package com.app.tienda1.Repositories;

import com.app.tienda1.Models.Carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    Optional<Carrito> findByClienteIdAndActivo(int clienteId, boolean activo);
}
