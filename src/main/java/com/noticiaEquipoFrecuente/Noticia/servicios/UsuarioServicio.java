package com.noticiaEquipoFrecuente.Noticia.servicios;

import com.noticiaEquipoFrecuente.Noticia.Enumeradores.Rol;
import com.noticiaEquipoFrecuente.Noticia.entidades.Noticia;
import com.noticiaEquipoFrecuente.Noticia.entidades.Usuario;
import com.noticiaEquipoFrecuente.Noticia.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(String nombreUsuario, String password, String password2) throws Exception {
        validar(nombreUsuario, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(nombreUsuario);

        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setFechaDeAlta(new Date());

        usuario.setRol(Rol.USER);

        usuario.setActivo(Boolean.TRUE);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificar(String nombreUsuario, String password, String password2, String idUsuario) throws Exception {
        validar(nombreUsuario, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setNombreUsuario(nombreUsuario);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(usuario.getRol());

            usuarioRepositorio.save(usuario);
        }
    }

    public void validarContraseña(String idUsuario, String password) throws Exception {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        boolean esCorrecta;
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            esCorrecta = new BCryptPasswordEncoder().matches(password, usuario.getPassword());
            if (!esCorrecta) {
                throw new Exception("La contraseña es incorrecta");
            }
        }
    }

    private void validar(String nombreUsuario, String password, String password2) throws Exception {
        if (nombreUsuario.isEmpty() || nombreUsuario == null || nombreUsuario.equalsIgnoreCase(" ")) {
            throw new Exception("El nombre de usuario no puede ser nulo o estar vacio.");
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

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorNombre(nombre);
        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);

        } else {
            return null;
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> Usuarios = usuarioRepositorio.ListarUsuarios();
        return Usuarios;
    }

    public List<Usuario> BuscarPorNombre(String nombre) {
        return usuarioRepositorio.ListarPorNombre(nombre);
    }

    @Transactional
    public void eliminarUsuario(String idUsuario) throws Exception {
        validarID(idUsuario);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuarioRepositorio.delete(usuario);
        }
    }

    private void validarID(String idUsuario) throws Exception {
        if (idUsuario.isEmpty() || idUsuario == null || idUsuario.equalsIgnoreCase(" ")) {
            throw new Exception("El ID del Usuario no puede ser nulo o estar vacio.");
        }
    }

    @Transactional
    public void estado(String idUsuario, Boolean activo) throws Exception {
        validarID(idUsuario);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getActivo() == true) {
                usuario.setActivo(false);
            } else {
                usuario.setActivo(true);
            }
        }
    }
}
