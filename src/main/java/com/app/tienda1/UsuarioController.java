package com.app.tienda1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService; // Inyectamos el servicio de productos

    @GetMapping("/")
    public String index(Model model) {
        // Obtener la lista de productos activos
        List<Producto> productos = productoService.obtenerProductosActivos();
        model.addAttribute("productos", productos);

        return "index.html"; // Vista principal
    }

    @GetMapping("/quienesSomos")
    public String quienesSomos() {
        return "quienesSomos"; // Vista de "Quiénes Somos"
    }

    @GetMapping("/users")
    public String users(Model model) {
        // Obtener la lista de usuarios
            List<Usuario> usuarios = usuarioRepository.findAll();
            model.addAttribute("usuarios", usuarios);
        return "users"; // Vista de prueba
    }
}