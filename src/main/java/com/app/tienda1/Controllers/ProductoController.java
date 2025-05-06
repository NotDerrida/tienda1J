package com.app.tienda1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.tienda1.Models.Producto;
import com.app.tienda1.Services.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos") // Cambiar la ruta para evitar conflicto
    public String listarProductosPorCategoria(Model model) {
        Map<String, List<Producto>> productosPorCategoria = productoService.obtenerProductosPorCategoria();
        model.addAttribute("productosPorCategoria", productosPorCategoria);
        return "index"; // Renderiza la página principal con productos agrupados
    }
}