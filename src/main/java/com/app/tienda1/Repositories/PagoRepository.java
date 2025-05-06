package com.app.tienda1.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tienda1.Models.Pago;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByCarrito_ClienteId(int clienteId);
}