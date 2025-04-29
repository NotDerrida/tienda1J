package com.app.tienda1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            return "redirect:/login";
        }

        carritoService.agregarProductoAlCarrito(cliente, productoId, cantidad);
        redirectAttributes.addFlashAttribute("success", "Producto agregado al carrito.");
        return "redirect:/";
    }
}