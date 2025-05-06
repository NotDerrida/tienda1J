package com.app.tienda1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByCarrito_ClienteId(int clienteId);
}