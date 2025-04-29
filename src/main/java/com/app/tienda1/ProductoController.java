package com.app.tienda1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.obtenerProductosActivos();
        model.addAttribute("productos", productos);
        return "productos"; // Vista específica para productos
    }
}