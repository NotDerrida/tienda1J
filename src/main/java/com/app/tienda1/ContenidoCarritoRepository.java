package com.app.tienda1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ContenidoCarritoRepository extends JpaRepository<ContenidoCarrito, Integer> {
    Optional<ContenidoCarrito> findByCarritoIdAndProductoId(Integer idCarrito, Integer productoId);
}