package com.app.tienda1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ContenidoCarritoRepository contenidoCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Busca el carrito activo de un usuario por su ID.
     *
     * @param clienteId ID del cliente
     * @return Carrito activo del usuario
     */
    public Carrito obtenerCarritoActivo(int clienteId) {
        return carritoRepository.findByClienteIdAndActivo(clienteId, true)
                .orElseGet(() -> {
                    // Crear un nuevo carrito si no existe uno activo
                    Carrito nuevoCarrito = new Carrito();
                    Usuario cliente = new Usuario();
                    cliente.setId(clienteId);
                    nuevoCarrito.setCliente(cliente);
                    nuevoCarrito.setActivo(true);
                    return carritoRepository.save(nuevoCarrito);
                });
    }

    /**
     * Agrega un producto al carrito del usuario.
     *
     * @param clienteId  ID del cliente
     * @param productoId ID del producto
     * @param cantidad   Cantidad a agregar
     */
    public void agregarProductoAlCarrito(int clienteId, int productoId, int cantidad) {
        // Obtener o crear el carrito activo del usuario
        Carrito carrito = obtenerCarritoActivo(clienteId);

        // Buscar el producto en la base de datos
        Producto producto = productoRepository.findById(productoId)
                .orElse(null); // Retorna null si el producto no existe

        if (producto == null) {
            return;
        } // Manejar el caso donde el producto no existe

        // Verificar si el producto ya está en el carrito
        Optional<ContenidoCarrito> contenidoExistente = contenidoCarritoRepository
                .findByCarritoIdAndProductoId(carrito.getIdCarrito(), productoId);

        if (contenidoExistente.isPresent()) {
            // Actualizar la cantidad si el producto ya está en el carrito
            ContenidoCarrito contenido = contenidoExistente.get();
            contenido.setCantidad(contenido.getCantidad() + cantidad);
            contenidoCarritoRepository.save(contenido);
        } else {
            // Agregar un nuevo producto al carrito
            ContenidoCarrito nuevoContenido = new ContenidoCarrito();
            nuevoContenido.setCarrito(carrito);
            nuevoContenido.setProducto(producto);
            nuevoContenido.setCantidad(cantidad);
            contenidoCarritoRepository.save(nuevoContenido);
        }
    }
}