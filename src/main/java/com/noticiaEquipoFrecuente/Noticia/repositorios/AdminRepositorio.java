package com.noticiaEquipoFrecuente.Noticia.repositorios;

import com.noticiaEquipoFrecuente.Noticia.entidades.Administrador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepositorio extends JpaRepository<Administrador, String> {

    @Query("SELECT a FROM Administrador a WHERE a.rol = 'ADMIN'")
    public List<Administrador> ListarAdministradores();

    @Query("SELECT a FROM Administrador a WHERE a.nombreUsuario LIKE %:nombreUsuario% AND a.rol = 'ADMIN'")
    public List<Administrador> ListarPorNombre(@Param("nombreUsuario") String nombreUsuario);
}
