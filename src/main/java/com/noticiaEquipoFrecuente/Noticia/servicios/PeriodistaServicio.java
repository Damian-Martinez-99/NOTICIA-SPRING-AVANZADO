package com.noticiaEquipoFrecuente.Noticia.servicios;

import com.noticiaEquipoFrecuente.Noticia.Enumeradores.Rol;
import com.noticiaEquipoFrecuente.Noticia.entidades.Periodista;
import com.noticiaEquipoFrecuente.Noticia.repositorios.PeriodistaRepositorio;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PeriodistaServicio {

    @Autowired
    private PeriodistaRepositorio periodistaRepositorio;

    public void registrar(String nombreUsuario, Integer sueldoMensual, String password, String password2) throws Exception {

        validar(nombreUsuario, sueldoMensual, password, password2);

        Periodista periodista = new Periodista();

        periodista.setNombreUsuario(nombreUsuario);
        
        periodista.setSueldoMensual(sueldoMensual);

        periodista.setPassword(new BCryptPasswordEncoder().encode(password));

        periodista.setFechaDeAlta(new Date());

        periodista.setRol(Rol.PERIODISTA);

        periodistaRepositorio.save(periodista);
    }

    private void validar(String nombreUsuario, Integer sueldoMensual, String password, String password2) throws Exception {
        if (nombreUsuario.isEmpty() || nombreUsuario == null || nombreUsuario.equalsIgnoreCase(" ")) {
            throw new Exception("El nombre de periodista no puede ser nulo o estar vacio.");
        }
        if (password.isEmpty() || password == null || password.equalsIgnoreCase(" ")) {
            throw new Exception("El password no puede ser nulo o estar vacio.");
        }
        if (password.length() < 6) {
            throw new Exception("El password debe contener al menos 6 caracteres.");
        }
        if (!password.equals(password2)) {
            throw new Exception("Las contraseñas no coinciden.");
        }
        if (sueldoMensual == null || sueldoMensual <= 0) {
            throw new Exception("Necesita cargar un valor posivito.");
        }
    }
}
