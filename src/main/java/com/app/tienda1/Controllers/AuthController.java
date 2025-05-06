package com.app.tienda1.Controllers;

import org.springframework.stereotype.Controller;
import com.app.tienda1.Repositories.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.app.tienda1.Services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.app.tienda1.Models.Usuario; // Ensure this is the correct package for the Usuario class

import java.util.Date;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    // Redirige al modal de login desde la página principal
    @GetMapping("/login")
    public String login() {
        return "redirect:/";
    }

    // Maneja el inicio de sesión
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario == null || !usuarioService.verifyPassword(password, usuario.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas.");
            return "redirect:/";
        }

        session.setAttribute("usuario", usuario); // Configurar el usuario en la sesión
        return "redirect:/";
    }

    // Cierra sesión y borra todos los datos de la sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Muestra el modal de registro
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Maneja el registro de nuevos usuarios
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setPassword(password); // La contraseña se encriptará en el servicio
            nuevoUsuario.setFechaRegistro(new Date());

            // Usa el servicio para crear el usuario
            usuarioService.crearUsuario(nuevoUsuario);

            // Mensaje de éxito
            redirectAttributes.addFlashAttribute("success", "Usuario registrado exitosamente.");
            return "redirect:/";
        } catch (Exception e) {
            // Mensaje de error
            redirectAttributes.addFlashAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            return "redirect:/";
        }
    }
}