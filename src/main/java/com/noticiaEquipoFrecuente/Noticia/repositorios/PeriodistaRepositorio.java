package com.noticiaEquipoFrecuente.Noticia.repositorios;

import com.noticiaEquipoFrecuente.Noticia.entidades.Periodista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodistaRepositorio extends JpaRepository<Periodista, String> {

    @Query("SELECT p FROM Periodista p WHERE p.rol = 'PERIODISTA'")
    public List<Periodista> ListarPeriodistas();

    @Query("SELECT p FROM Periodista p WHERE p.nombreUsuario LIKE %:nombreUsuario% AND p.rol = 'PERIODISTA'")
    public List<Periodista> ListarPorNombre(@Param("nombreUsuario") String nombreUsuario);
}
