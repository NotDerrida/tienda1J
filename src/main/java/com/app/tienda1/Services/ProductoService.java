package com.app.tienda1.Services;

import org.springframework.stereotype.Service;
import com.app.tienda1.Repositories.ProductoRepository;
import com.app.tienda1.Models.Producto; // Ensure this path matches the actual location of Producto class
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerProductosActivos() {
        return productoRepository.findByActivoTrue();
    }

    public Map<String, List<Producto>> obtenerProductosPorCategoria() {
        List<Producto> productos = productoRepository.findByActivoTrue();
        return productos.stream()
                .collect(Collectors.groupingBy(producto -> {
                    // Aquí usamos un método ficticio para obtener el nombre de la categoría
                    String categoriaNombre = obtenerNombreCategoriaPorId(producto.getCategoriaId());
                    return categoriaNombre != null ? categoriaNombre : "Sin Categoría";
                }));
    }

    // Método auxiliar para obtener el nombre de la categoría
    private String obtenerNombreCategoriaPorId(Integer categoriaId) {
        // Simula una consulta a la base de datos o un mapa de categorías
        switch (categoriaId) {
            case 1:
                return "Accesorios Tecnológicos";
            case 2:
                return "Hardware";
            case 3:
                return "Libros y Recursos Educativos";
            case 4:
                return "Servicios en la Nube";
            case 5:
                return "Software";
            default:
                return "Sin Categoría";
        }
    }
}