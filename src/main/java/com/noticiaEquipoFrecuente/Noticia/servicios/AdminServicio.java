package com.noticiaEquipoFrecuente.Noticia.servicios;

import com.noticiaEquipoFrecuente.Noticia.Enumeradores.Rol;
import com.noticiaEquipoFrecuente.Noticia.entidades.Administrador;
import com.noticiaEquipoFrecuente.Noticia.repositorios.AdminRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServicio {
    
    @Autowired
    private AdminRepositorio adminRepositorio;
    
    public void registrar(String nombreUsuario, String password, String password2) throws Exception {
        validar(nombreUsuario, password, password2);

        Administrador admin = new Administrador();

        admin.setNombreUsuario(nombreUsuario);

        admin.setPassword(new BCryptPasswordEncoder().encode(password));

        admin.setFechaDeAlta(new Date());

        admin.setRol(Rol.ADMIN);

        adminRepositorio.save(admin);
    }

    private void validar(String nombreUsuario, String password, String password2) throws Exception {
        if (nombreUsuario.isEmpty() || nombreUsuario == null || nombreUsuario.equalsIgnoreCase(" ")) {
            throw new Exception("El nombre de admin no puede ser nulo o estar vacio.");
        }
        if (password.isEmpty() || password == null || password.equalsIgnoreCase(" ")) {
            throw new Exception("El password no puede ser nulo o estar vacio.");
        }
        if (password.length() < 6) {
            throw new Exception("El password debe contener al menos 6 caracteres.");
        }
        if (!password.equals(password2)) {
            throw new Exception("Las contraseñas no coinciden");
        }
    }
    
}
