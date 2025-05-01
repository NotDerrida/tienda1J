package com.app.tienda1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
    List<Pedido> findByPago_Carrito_ClienteId(int clienteId);
}
