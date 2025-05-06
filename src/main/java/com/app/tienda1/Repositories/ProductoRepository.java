package com.app.tienda1.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.tienda1.Models.Producto;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByActivoTrue();
}