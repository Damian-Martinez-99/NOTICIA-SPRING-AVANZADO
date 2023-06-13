package com.noticiaEquipoFrecuente.Noticia.repositorios;

import com.noticiaEquipoFrecuente.Noticia.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombre(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    public Usuario buscarPorId(@Param("idUsuario") String idUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'USER'")
    public List<Usuario> ListarUsuarios();

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario LIKE %:nombreUsuario% AND u.rol = 'USER'")
    public List<Usuario> ListarPorNombre(@Param("nombreUsuario") String nombreUsuario);
}
