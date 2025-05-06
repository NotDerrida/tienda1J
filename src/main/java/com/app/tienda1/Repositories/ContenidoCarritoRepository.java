package com.app.tienda1.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tienda1.Models.Carrito;
import com.app.tienda1.Models.ContenidoCarrito;
import com.app.tienda1.Models.Producto;

import java.util.Optional;

public interface ContenidoCarritoRepository extends JpaRepository<ContenidoCarrito, Integer> {
    Optional<ContenidoCarrito> findByCarritoAndProducto(Carrito carrito, Producto producto);

}