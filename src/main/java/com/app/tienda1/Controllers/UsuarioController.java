package com.app.tienda1.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.tienda1.Models.Carrito;
import com.app.tienda1.Models.Producto;
import com.app.tienda1.Models.Usuario;
import com.app.tienda1.Repositories.UsuarioRepository;
import com.app.tienda1.Services.CarritoService;
import com.app.tienda1.Services.ProductoService;
import com.app.tienda1.Services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService; // Inyectamos el servicio de productos

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // Obtener la lista de productos activos
        Map<String, List<Producto>> productosPorCategoria = productoService.obtenerProductosPorCategoria();
        model.addAttribute("productosPorCategoria", productosPorCategoria);

        // Cargar el carrito activo del usuario si está autenticado
        Usuario cliente = (Usuario) session.getAttribute("usuario");
        if (cliente != null) {
            Carrito carrito = carritoService.obtenerCarritoActivo(cliente.getId());
            model.addAttribute("carrito", carrito);
        }

        return "index"; // Renderiza la página principal
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