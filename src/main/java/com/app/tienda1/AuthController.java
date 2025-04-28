package com.app.tienda1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.Date;


@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "redirect:/"; // Usa el modal en la página principal
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, 
                            @RequestParam String password, 
                            HttpSession session) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElse(null);

        if (usuario == null || !usuarioService.verifyPassword(password, usuario.getPassword())) {
            session.setAttribute("error", "Credenciales incorrectas.");
            System.out.println("Error agregado a la sesión: " + session.getAttribute("error"));
            return "redirect:/";
        }

        session.setAttribute("usuario", usuario);
        return "redirect:/";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Esto borra todo de la sesión
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Usa una vista separada para el registro
    }
    
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setFechaRegistro(new Date()); 

            // Usar el servicio para crear el usuario
            usuarioService.crearUsuario(nuevoUsuario);

            session.setAttribute("success", "Usuario registrado exitosamente.");
            return "redirect:/";
        } catch (Exception e) {
            session.setAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "redirect:/index";
        }
    }
}