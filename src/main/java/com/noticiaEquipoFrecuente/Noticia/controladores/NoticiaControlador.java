package com.noticiaEquipoFrecuente.Noticia.controladores;

import com.noticiaEquipoFrecuente.Noticia.entidades.Noticia;
import com.noticiaEquipoFrecuente.Noticia.entidades.Periodista;
import com.noticiaEquipoFrecuente.Noticia.entidades.Usuario;
import com.noticiaEquipoFrecuente.Noticia.servicios.NoticiaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/{idNoticia}")
    public String idNoticia(@PathVariable String idNoticia, ModelMap modelo, HttpSession sesion) {

        try {
            Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
            if (usuario.getRol().toString().equals("PERIODISTA")) {
                Periodista periodista = (Periodista) sesion.getAttribute("usuariosession");
                modelo.put("usuario", periodista);
            } else {
                modelo.put("usuario", usuario);
            }
            modelo.put("noticia", noticiaServicio.BuscarPorID(idNoticia));
            modelo.put("noticias", noticiaServicio.Recientes());
            return "noticia.html";
        } catch (Exception e) {
            modelo.put("noticia", noticiaServicio.BuscarPorID(idNoticia));
            modelo.put("noticias", noticiaServicio.Recientes());
            return "noticia.html";
        }
    }

    @PreAuthorize("hasRole('ROLE_PERIODISTA')")
    @GetMapping("/creacionNoticia")
    public String creacionNoticia(ModelMap modelo) {
        return "noticia_form.html";
    }

    @PostMapping("/ingresoNoticia")
    public String ingresoNoticia(@RequestParam(required = false) String titulo, @RequestParam(required = false) String cuerpo, HttpSession sesion, ModelMap modelo, RedirectAttributes redireccion) {
        try {
            noticiaServicio.CrearNoticia(titulo, cuerpo, sesion);
//            redireccion.addFlashAttribute("exito", "La noticia fue cargada con exito!");
            redireccion.addAttribute("exito", "La noticia fue cargada con exito!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_form.html";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/modificarNoticia/{idNoticia}")
    public String modificarNoticia(@PathVariable String idNoticia, ModelMap modelo, String error) {
        modelo.put("noticia", noticiaServicio.BuscarPorID(idNoticia));
        modelo.put("error", error);
        return "noticia_modificar.html";
    }

    @PostMapping("/modificacionNoticia/{idNoticia}")
    public String modificacionNoticia(@RequestParam(required = false) String titulo, @RequestParam(required = false) String cuerpo, @PathVariable String idNoticia, ModelMap modelo, RedirectAttributes redireccion) {
        try {
            noticiaServicio.ModificarNoticia(titulo, cuerpo, idNoticia);
//            redireccion.addFlashAttribute("exito", "La noticia fue modificada con exito!");
            redireccion.addAttribute("exito", "La noticia fue modificada con exito!");
        } catch (Exception ex) {
//            redireccion.addFlashAttribute("error", ex.getMessage());
            redireccion.addAttribute("error", ex.getMessage());
            return "redirect:/noticia/modificarNoticia/" + idNoticia;
        }
        modelo.put("noticias", noticiaServicio.Recientes());
        return "redirect:/";
    }

//    @GetMapping("/eliminarNoticia/{idNoticia}")
//    public String eliminarNoticia(@PathVariable String idNoticia, ModelMap modelo) {
//        modelo.put("noticia", noticiaServicio.BuscarPorID(idNoticia));
//        return "noticia_eliminar.html";
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminacionNoticia/{idNoticia}")
    public String eliminacionNoticia(@PathVariable String idNoticia, ModelMap modelo, RedirectAttributes redireccion) {
        modelo.put("noticia", noticiaServicio.BuscarPorID(idNoticia));
        try {
            noticiaServicio.EliminarNoticia(idNoticia);
//            redireccion.addFlashAttribute("exito", "La noticia fue eliminada con exito!");
            redireccion.addAttribute("exito", "La noticia fue eliminada con exito!");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "/{idNoticia}";
        }
        return "redirect:/";
    }

    @GetMapping("/noticiasGenerales")
    public String noticiasGenerales(ModelMap modelo, HttpSession sesion) {
        modelo.put("busqueda", noticiaServicio.Recientes());
        modelo.put("noticias", noticiaServicio.Recientes());
        try {
            Usuario usuario = (Usuario) sesion.getAttribute("usuariosession");
            Periodista periodista = null;
            if (usuario.getRol().toString().equals("PERIODISTA")) {
                periodista = (Periodista) sesion.getAttribute("usuariosession");
                modelo.put("usuario", periodista);
            } else {
                modelo.put("usuario", usuario);
            }
            return "noticias_generales.html";
        } catch (Exception e) {
            return "noticias_generales.html";
        }
    }

    @PostMapping("/noticiaBuscada")
    public String noticiasBuscada(ModelMap modelo, String titulo) {
        modelo.put("noticias", noticiaServicio.Recientes());
        if (titulo == null) {
            modelo.put("busqueda", noticiaServicio.Recientes());
        } else {
            List<Noticia> busqueda = noticiaServicio.BuscarPorTitulo(titulo);
            if (busqueda == null || busqueda.isEmpty()) {
                modelo.put("error", "La noticia buscada no existe.");
            } else {
                modelo.put("busqueda", busqueda);
            }
        }
        return "noticias_generales.html";
    }
}
