package com.app.tienda1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/")
    public String index(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "index.html";
    }

    @GetMapping("/quienesSomos")
    public String quienesSomos() {
        return "quienesSomos";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /*@PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
    usuarioService.crearUsuario(usuario);
    return "redirect:/"; // O a donde quieras redirigir después del registro
}*/

}