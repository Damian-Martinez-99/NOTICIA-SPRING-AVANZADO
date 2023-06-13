package com.noticiaEquipoFrecuente.Noticia.controladores;

import com.noticiaEquipoFrecuente.Noticia.entidades.Administrador;
import com.noticiaEquipoFrecuente.Noticia.entidades.Periodista;
import com.noticiaEquipoFrecuente.Noticia.entidades.Usuario;
import com.noticiaEquipoFrecuente.Noticia.servicios.AdminServicio;
import com.noticiaEquipoFrecuente.Noticia.servicios.NoticiaServicio;
import com.noticiaEquipoFrecuente.Noticia.servicios.PeriodistaServicio;
import com.noticiaEquipoFrecuente.Noticia.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private PeriodistaServicio periodistaServicio;

    @Autowired
    private AdminServicio adminServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private NoticiaServicio noticiaServicio;

    @GetMapping("/registrarPeriodista")
    public String registrarPeriodista() {

        return "registro_periodista.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroPeriodista")
    public String registroPeriodista(@RequestParam String nombre, Integer sueldoMensual, @RequestParam String password,
            String password2, ModelMap modelo, RedirectAttributes redireccion) {
        try {
            periodistaServicio.registrar(nombre, sueldoMensual, password, password2);
            modelo.put("exito", "El periodista se registró exitosamente!");
            redireccion.addAttribute("exito", "El periodista se registró exitosamente!");
            return "redirect:/";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registro_periodista.html";
        }
    }

    @GetMapping("/registrarAdmin")
    public String registrarAdmin() {

        return "registro_admin.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/registroAdmin")
    public String registroAdmin(@RequestParam String nombre, @RequestParam String password,
            String password2, ModelMap modelo, RedirectAttributes redireccion) {
        try {
            adminServicio.registrar(nombre, password, password2);
            modelo.put("exito", "El admin se registró exitosamente!");
            redireccion.addAttribute("exito", "El admin se registró exitosamente!");
            return "redirect:/";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registro_admin.html";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listaAdministradores")
    public String listaAdministradores(ModelMap modelo) {
        modelo.put("admins", adminServicio.listarAdministradores());
        modelo.put("noticias", noticiaServicio.Recientes());
        modelo.put("busqueda", adminServicio.listarAdministradores());
        return "lista_administradores.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/administradorBuscado")
    public String administradorBuscado(ModelMap modelo, String nombre) {
        modelo.put("usuarios", adminServicio.listarAdministradores());
        if (nombre == null) {
            modelo.put("busqueda", adminServicio.listarAdministradores());
        } else {
            List<Administrador> busqueda = adminServicio.BuscarPorNombre(nombre);
            if (busqueda == null || busqueda.isEmpty() || nombre.isEmpty()) {
                modelo.put("error", "El administrador no se encuentra registrado.");
            } else if (!nombre.isEmpty()) {
                modelo.put("busqueda", busqueda);
            }
        }
        return "lista_administradores.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listaPeriodistas")
    public String listaPeriodistas(ModelMap modelo) {
        modelo.put("periodistas", periodistaServicio.listarPeriodistas());
        modelo.put("noticias", noticiaServicio.Recientes());
        modelo.put("busqueda", periodistaServicio.listarPeriodistas());
        return "lista_periodistas.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/periodistaBuscado")
    public String periodistaBuscado(ModelMap modelo, String nombre) {
        modelo.put("usuarios", periodistaServicio.listarPeriodistas());
        if (nombre == null) {
            modelo.put("busqueda", periodistaServicio.listarPeriodistas());
        } else {
            List<Periodista> busqueda = periodistaServicio.BuscarPorNombre(nombre);
            if (busqueda == null || busqueda.isEmpty() || nombre.isEmpty()) {
                modelo.put("error", "El periodista no se encuentra registrado.");
            } else if (!nombre.isEmpty()) {
                modelo.put("busqueda", busqueda);
            }
        }
        return "lista_periodistas.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listaUsuarios")
    public String listaUsuarios(ModelMap modelo) {
        modelo.put("usuarios", usuarioServicio.listarUsuarios());
        modelo.put("noticias", noticiaServicio.Recientes());
        modelo.put("busqueda", usuarioServicio.listarUsuarios());
        return "lista_usuarios.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/usuarioBuscado")
    public String usuarioBuscado(ModelMap modelo, String nombre) {
        modelo.put("usuarios", usuarioServicio.listarUsuarios());
        if (nombre == null) {
            modelo.put("busqueda", usuarioServicio.listarUsuarios());
        } else {
            List<Usuario> busqueda = usuarioServicio.BuscarPorNombre(nombre);
            if (busqueda == null || busqueda.isEmpty() || nombre.isEmpty()) {
                modelo.put("error", "El usuario no se encuentra registrado.");
            } else if (!nombre.isEmpty()) {
                modelo.put("busqueda", busqueda);
            }
        }
        return "lista_usuarios.html";
    }
}
