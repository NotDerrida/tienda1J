package com.app.tienda1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

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
    public String eliminarProducto(@RequestParam Integer productoId, HttpSession session, RedirectAttributes redirectAttributes) {
    Usuario cliente = (Usuario) session.getAttribute("usuario");
    if (cliente == null) {
        redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para modificar el carrito.");
        return "redirect:/";
    }

    carritoService.eliminarProductoDelCarrito(cliente.getId(), productoId.intValue());
    redirectAttributes.addFlashAttribute("success", "Producto eliminado del carrito.");
    return "redirect:/carrito/carrito/ver"; // Redirige de nuevo a ver el carrito
    }
}