package com.app.tienda1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ContenidoCarritoRepository contenidoCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Carrito obtenerCarritoActivo(Usuario cliente) {
        return carritoRepository.findByClienteAndActivo(cliente, true)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.setCliente(cliente);
                    nuevoCarrito.setActivo(true);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    public void agregarProductoAlCarrito(Usuario cliente, Integer productoId, Integer cantidad) {
        Carrito carrito = obtenerCarritoActivo(cliente);
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        ContenidoCarrito contenido = carrito.getContenido().stream()
                .filter(c -> c.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseGet(() -> {
                    ContenidoCarrito nuevoContenido = new ContenidoCarrito();
                    nuevoContenido.setCarrito(carrito);
                    nuevoContenido.setProducto(producto);
                    nuevoContenido.setCantidad(0);
                    return nuevoContenido;
                });

        contenido.setCantidad(contenido.getCantidad() + cantidad);
        contenidoCarritoRepository.save(contenido);
    }
}