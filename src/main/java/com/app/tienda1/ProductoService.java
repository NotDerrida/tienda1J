package com.app.tienda1;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerProductosActivos() {
        // Suponiendo que tienes un campo "activo" en la entidad Producto PRUEBA PRUEBA
        return productoRepository.findByActivoTrue();
    }
}