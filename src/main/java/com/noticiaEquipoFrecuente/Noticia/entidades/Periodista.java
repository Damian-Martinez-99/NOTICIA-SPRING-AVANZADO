package com.noticiaEquipoFrecuente.Noticia.entidades;

import com.noticiaEquipoFrecuente.Noticia.Enumeradores.Rol;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Periodista extends Usuario {

    private ArrayList<Noticia> misNoticias;
    private Integer sueldoMensual;

    public Periodista() {
    }

    public Periodista(ArrayList<Noticia> misNoticias, Integer sueldoMensual, String idUsuario, String nombreUsuario, String password, Date fechaDeAlta, Rol rol, Boolean activo) {
        super(idUsuario, nombreUsuario, password, fechaDeAlta, rol, activo);
        this.misNoticias = misNoticias;
        this.sueldoMensual = sueldoMensual;
    }

    public ArrayList<Noticia> getMisNoticias() {
        return misNoticias;
    }

    public void setMisNoticias(ArrayList<Noticia> misNoticias) {
        this.misNoticias = misNoticias;
    }

    public Integer getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Integer sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

    @Override
    public String toString() {
        return "Periodista{" + "misNoticias=" + misNoticias + ", sueldoMensual=" + sueldoMensual + '}';
    }
}
