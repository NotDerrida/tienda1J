package com.app.tienda1.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import com.app.tienda1.Repositories.CarritoRepository;
import com.app.tienda1.Repositories.ContenidoCarritoRepository;
import com.app.tienda1.Repositories.ProductoRepository;
import com.app.tienda1.Models.Pedido;
import com.app.tienda1.Repositories.PagoRepository;
import com.app.tienda1.Repositories.PedidoRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

import com.app.tienda1.Models.ContenidoCarrito;
import com.app.tienda1.Models.Carrito;
import com.app.tienda1.Models.Carrito;
import com.app.tienda1.Models.Producto;
import com.app.tienda1.Models.Usuario;
import com.app.tienda1.Models.Pago;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ContenidoCarritoRepository contenidoCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Busca el carrito activo de un usuario por su ID.
     *
     * @param clienteId ID del cliente
     * @return Carrito activo del usuario
     */
    @Transactional
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
                .findByCarritoAndProducto(carrito, producto);

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

    /**
     * Elimina un producto del carrito del usuario.
     *
     * @param clienteId  ID del cliente
     * @param productoId ID del producto a eliminar
     */
    @Transactional
    public void eliminarProductoDelCarrito(int clienteId, int productoId) {
        Carrito carrito = obtenerCarritoActivo(clienteId);
        if (carrito == null) {
            System.out.println("No se encontró un carrito activo para el cliente con ID: " + clienteId);
            return;
        }

        // Buscar el producto en la lista de contenido del carrito
        carrito.getContenido().removeIf(contenido -> contenido.getProducto().getId().equals(productoId));

        // Guardar el carrito para que se reflejen los cambios
        carritoRepository.save(carrito);

        System.out.println("Producto con ID " + productoId + " eliminado del carrito.");
    }

    @Transactional
    public void realizarPago(int clienteId) {
        // Obtener el carrito activo del usuario
        Carrito carrito = carritoRepository.findByClienteIdAndActivo(clienteId, true)
                .orElseThrow(() -> new RuntimeException("No hay un carrito activo para realizar el pago."));

        // Calcular el total del carrito
        double total = carrito.getContenido().stream()
                .mapToDouble(c -> c.getProducto().getPrecio() * c.getCantidad())
                .sum();

        // Crear un registro en la tabla Pago
        Pago pago = new Pago();
        pago.setCarrito(carrito);
        pago.setNumReferencia(UUID.randomUUID().toString());
        pago.setFechaPago(LocalDateTime.now());
        pago.setTotalPago(total);
        pagoRepository.save(pago);

        // Crear un registro en la tabla Pedido
        Pedido pedido = new Pedido();
        pedido.setTicket("TICKET-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pedido.setPago(pago);
        pedido.setFechaEntrega(LocalDate.now().plusDays(7)); // Fecha de entrega en 7 días
        pedido.setEstado("pendiente");
        pedidoRepository.save(pedido);

        // Marcar el carrito como inactivo
        carrito.setActivo(false);
        carritoRepository.save(carrito);
    }

    /*
     * public void eliminarProductoDelCarrito(int clienteId, int productoId) {
     * // Obtener el carrito activo
     * System.out.println("Iniciando eliminación del producto ID: " + productoId);
     * Carrito carrito = obtenerCarritoActivo(clienteId);
     * System.out.println("Carrito actual: " + carrito);
     * 
     * if (carrito == null) {
     * return; // No hacer nada si no hay carrito
     * }
     * Producto producto = productoRepository.findById(productoId)
     * .orElse(null); // Retorna null si el producto no existe
     * if ((producto == null))
     * return; // Manejar el caso donde el producto no existe
     * 
     * Optional<ContenidoCarrito> contenido =
     * contenidoCarritoRepository.findByCarritoAndProducto(carrito, producto);
     * contenido.ifPresent(c -> contenidoCarritoRepository.delete(c));
     * 
     * if (contenido.isPresent()) {
     * contenidoCarritoRepository.delete(contenido.get());
     * System.out.println("Producto con ID " + productoId +
     * " eliminado del carrito.");
     * } else {
     * System.out.println("El producto con ID " + productoId +
     * " no se encontró en el carrito.");
     * }
     * // Si existe, eliminarlo
     * //contenido.ifPresent(c -> contenidoCarritoRepository.delete(c));
     * }
     */
}