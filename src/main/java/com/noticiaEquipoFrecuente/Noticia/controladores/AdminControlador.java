package com.noticiaEquipoFrecuente.Noticia.controladores;

import com.noticiaEquipoFrecuente.Noticia.servicios.AdminServicio;
import com.noticiaEquipoFrecuente.Noticia.servicios.PeriodistaServicio;
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
}
