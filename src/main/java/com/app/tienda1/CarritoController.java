package com.app.tienda1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private PagoRepository pagoRepository; // Inyección de PagoRepository

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam Integer productoId, @RequestParam Integer cantidad, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para agregar productos al carrito.");
            return "redirect:/";
        }

        carritoService.agregarProductoAlCarrito(cliente.getId(), productoId.intValue(), cantidad.intValue());
        redirectAttributes.addFlashAttribute("success", "Producto agregado al carrito.");
        return "redirect:/";
    }

    @GetMapping("/carrito/ver")
    public String verCarrito(HttpSession session, Model model) {
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        if (cliente == null) {
            return "redirect:/login";
        }

        Carrito carrito = carritoService.obtenerCarritoActivo(cliente.getId());
        model.addAttribute("carrito", carrito);
        // Imprimir el carrito en la consola para verificar su contenido
        System.out.println("Carrito cargado: " + carrito);

        // Forzar la carga de los productos del carrito
        if (carrito != null && carrito.getContenido() != null) {
            carrito.getContenido().size(); // Esto asegura que los productos se carguen
            System.out.println("Contenido del carrito: " + carrito.getContenido());
        }

        model.addAttribute("carrito", carrito);
        return "index"; // O la vista donde se muestra el carrito
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(@RequestParam Integer productoId, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para modificar el carrito.");
            return "redirect:/";
        }

        carritoService.eliminarProductoDelCarrito(cliente.getId(), productoId.intValue());
        redirectAttributes.addFlashAttribute("success", "Producto eliminado del carrito.");
        return "redirect:/"; // Redirige correctamente a la página principal
    }

    @GetMapping("/pago")
    public String mostrarFormularioPago(HttpSession session, Model model) {
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        if (cliente == null) {
            return "redirect:/login";
        }

        Carrito carrito = carritoService.obtenerCarritoActivo(cliente.getId());
        if (carrito == null || carrito.getContenido().isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("carrito", carrito);
        return "pago"; // Vista para ingresar método de pago y dirección
    }

    @PostMapping("/pagar")
    public String realizarPago(@RequestParam String metodoPago, @RequestParam String direccion, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        System.out.println("Usuario en sesión: " + cliente); // Verificar si el usuario está en sesión
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para realizar el pago.");
            return "redirect:/login";
        }

        try {
            carritoService.realizarPago(cliente.getId());
            redirectAttributes.addFlashAttribute("success", "Pago realizado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hubo un problema al procesar el pago.");
        }

        return "redirect:/";
    }

    @GetMapping("/pedidos/json")
    @ResponseBody
    public List<PedidoDTO> obtenerPedidos(HttpSession session) {
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        if (cliente == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        List<Pedido> pedidos = pedidoRepository.findByPago_Carrito_ClienteId(cliente.getId());
        if (pedidos.isEmpty()) {
            throw new RuntimeException("No se encontraron pedidos para este usuario");
        }

        // Convertir la lista de pedidos a una lista de PedidoDTO
        List<PedidoDTO> pedidosDTO = pedidos.stream()
                .map(pedido -> {
                    // Obtener los nombres de los productos comprados en este pedido
                    List<String> productos = pedido.getPago().getCarrito().getContenido().stream()
                            .map(contenido -> contenido.getProducto().getNombre() + " (Cantidad: "
                                    + contenido.getCantidad() + ")")
                            .toList();

                    return new PedidoDTO(
                            pedido.getTicket(),
                            pedido.getPago().getTotalPago(),
                            pedido.getFechaEntrega().toString(),
                            pedido.getEstado(),
                            productos);
                })
                .toList();

        return pedidosDTO;
    }
}