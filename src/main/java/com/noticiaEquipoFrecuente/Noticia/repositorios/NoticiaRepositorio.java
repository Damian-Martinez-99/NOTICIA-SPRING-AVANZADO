package com.noticiaEquipoFrecuente.Noticia.repositorios;

import com.noticiaEquipoFrecuente.Noticia.entidades.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String> {

    @Query("SELECT n FROM Noticia n WHERE n.titulo LIKE %:titulo%")
    public List<Noticia> BuscarPorTitulo(@Param("titulo") String titulo);

//    @Query("SELECT n FROM Noticia n WHERE n.cuerpo LIKE :%cuerpo%")
    @Query("SELECT n FROM Noticia n WHERE n.cuerpo LIKE %:cuerpo%")
    public List<Noticia> BuscarPalabraEnCuerpo(@Param("cuerpo") String cuerpo);

    @Query("SELECT n FROM Noticia n ORDER BY n.fecha DESC")
    public List<Noticia> ListarRecientes();

    @Query("SELECT n FROM Noticia n WHERE n.creador.idUsuario = :creador")
    public List<Noticia> ListarMisNoticias(@Param("creador") String creador);

    @Query("SELECT n FROM Noticia n WHERE n.creador.idUsuario = :creador AND n.titulo LIKE %:titulo%")
    public List<Noticia> BuscarMisNoticias(@Param("creador") String creador, @Param("titulo") String titulo);
}
