package com.app.tienda1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("productos", productoService.obtenerProductosActivos());
        return "index"; // Usa tu plantilla index.html
    }
}