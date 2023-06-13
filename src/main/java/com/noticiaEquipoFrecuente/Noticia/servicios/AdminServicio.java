package com.noticiaEquipoFrecuente.Noticia.servicios;

import com.noticiaEquipoFrecuente.Noticia.Enumeradores.Rol;
import com.noticiaEquipoFrecuente.Noticia.entidades.Administrador;
import com.noticiaEquipoFrecuente.Noticia.repositorios.AdminRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
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
        
        admin.setActivo(Boolean.TRUE);

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

    public List<Administrador> listarAdministradores() {
        List<Administrador> Admins = adminRepositorio.findAll();
        return Admins;
    }

    public List<Administrador> BuscarPorNombre(String nombre) {
        return adminRepositorio.ListarPorNombre(nombre);
    }

    @Transactional
    public void eliminarAdmin(String idAdmin) throws Exception {
        validarID(idAdmin);

        Optional<Administrador> respuesta = adminRepositorio.findById(idAdmin);

        if (respuesta.isPresent()) {
            Administrador admin = respuesta.get();

            adminRepositorio.delete(admin);
        }
    }

    private void validarID(String idAdmin) throws Exception {
        if (idAdmin.isEmpty() || idAdmin == null || idAdmin.equalsIgnoreCase(" ")) {
            throw new Exception("El ID del Admin no puede ser nulo o estar vacio.");
        }
    }
}
