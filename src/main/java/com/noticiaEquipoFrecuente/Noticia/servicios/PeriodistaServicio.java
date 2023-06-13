package com.noticiaEquipoFrecuente.Noticia.servicios;

import com.noticiaEquipoFrecuente.Noticia.Enumeradores.Rol;
import com.noticiaEquipoFrecuente.Noticia.entidades.Periodista;
import com.noticiaEquipoFrecuente.Noticia.repositorios.PeriodistaRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
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
        
        periodista.setActivo(Boolean.TRUE);

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

    public List<Periodista> listarPeriodistas() {
        List<Periodista> periodistas = periodistaRepositorio.findAll();
        return periodistas;
    }

    public List<Periodista> BuscarPorNombre(String nombre) {
        return periodistaRepositorio.ListarPorNombre(nombre);
    }

    public void eliminarPeriodista(String idPeriodista) throws Exception {
        validarID(idPeriodista);

        Optional<Periodista> respuesta = periodistaRepositorio.findById(idPeriodista);

        if (respuesta.isPresent()) {
            Periodista periodista = respuesta.get();

            periodistaRepositorio.delete(periodista);
        }
    }

    private void validarID(String idPeriodista) throws Exception {
        if (idPeriodista.isEmpty() || idPeriodista == null || idPeriodista.equalsIgnoreCase(" ")) {
            throw new Exception("El ID del Periodista no puede ser nulo o estar vacio.");
        }
    }
}
