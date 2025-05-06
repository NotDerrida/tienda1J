package com.app.tienda1.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.app.tienda1.Repositories.UsuarioRepository;
import com.app.tienda1.Models.Usuario;
import com.app.tienda1.Models.TipoUsuario;
import org.springframework.stereotype.Service;
import com.app.tienda1.Repositories.TipoUsuarioRepository;

import java.util.Date;

@Service
public class UsuarioService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {
        // Asignar la fecha de registro
        usuario.setFechaRegistro(new Date());

        // Asignar el tipo de usuario por defecto (id = 3)
        TipoUsuario tipoPorDefecto = tipoUsuarioRepository.findById(3)
                .orElseThrow(() -> new RuntimeException("No se encontró el tipo de usuario con ID 3"));
        usuario.setTipoUsuario(tipoPorDefecto);

        // Hashear la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }
}