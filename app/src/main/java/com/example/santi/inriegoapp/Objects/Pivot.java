package com.example.santi.inriegoapp.Objects;

import java.util.Date;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class Pivot {
    private String nombre;
    private String fenologia;
    private Date Fecha;

    public Pivot() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFenologia() {
        return fenologia;
    }

    public void setFenologia(String fenologia) {
        this.fenologia = fenologia;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }
}
