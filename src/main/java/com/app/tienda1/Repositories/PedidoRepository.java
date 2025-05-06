package com.app.tienda1.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tienda1.Models.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, String> {
    List<Pedido> findByPago_Carrito_ClienteId(int clienteId);
}
