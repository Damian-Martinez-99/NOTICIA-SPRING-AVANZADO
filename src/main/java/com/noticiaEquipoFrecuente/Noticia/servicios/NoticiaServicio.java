package com.noticiaEquipoFrecuente.Noticia.servicios;

import com.noticiaEquipoFrecuente.Noticia.entidades.Noticia;
import com.noticiaEquipoFrecuente.Noticia.entidades.Periodista;
import com.noticiaEquipoFrecuente.Noticia.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio notiRepo;

    @Transactional
    public void CrearNoticia(String titulo, String cuerpo, HttpSession sesion) throws Exception {
        Validar(titulo, cuerpo);

        Noticia noticia = new Noticia();

        Date fecha = new Date();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setFecha(fecha);
        noticia.setCreador((Periodista) sesion.getAttribute("usuariosession"));

        System.out.println(fecha);

        notiRepo.save(noticia);
    }

    @Transactional
    public void ModificarNoticia(String titulo, String cuerpo, String idNoticia) throws Exception {
        Validar(titulo, cuerpo);
        ValidarID(idNoticia);

        Optional<Noticia> respuesta = notiRepo.findById(idNoticia);

        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();

            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);

            notiRepo.save(noticia);
        }
    }

    public List<Noticia> ListarNoticias() {
        List<Noticia> noticias = new ArrayList();

        noticias = notiRepo.findAll();

        return noticias;
    }

    @Transactional
    public void EliminarNoticia(String idNoticia) throws Exception {
        ValidarID(idNoticia);

        Optional<Noticia> respuesta = notiRepo.findById(idNoticia);

        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();

            notiRepo.delete(noticia);
        }
    }

    private void Validar(String titulo, String cuerpo) throws Exception {
        if (titulo.isEmpty() || titulo == null || titulo.equalsIgnoreCase(" ")) {
            throw new Exception("El titulo no puede ser nulo o estar vacio.");
        }
        if (cuerpo.isEmpty() || cuerpo == null || cuerpo.equalsIgnoreCase(" ")) {
            throw new Exception("El cuerpo no puede ser nulo o estar vacio.");
        }
    }

    private void ValidarID(String idNoticia) throws Exception {
        if (idNoticia.isEmpty() || idNoticia == null || idNoticia.equalsIgnoreCase(" ")) {
            throw new Exception("El ID de la noticia no puede ser nulo o estar vacio.");
        }
    }

    public Noticia BuscarPorID(String idNoticia) {
        return notiRepo.findById(idNoticia).get();
    }

    public List<Noticia> Recientes() {
        return notiRepo.ListarRecientes();
    }

    public List<Noticia> BuscarPorTitulo(String titulo) {
        return notiRepo.BuscarPorTitulo(titulo);
    }

    public List<Noticia> ListarMisNoticias(Periodista creador) {
        return notiRepo.ListarMisNoticias(creador.getIdUsuario());
    }

    public List<Noticia> BuscarMisNoticias(Periodista creador, String titulo) {
        return notiRepo.BuscarMisNoticias(creador.getIdUsuario(), titulo);
    }
}
