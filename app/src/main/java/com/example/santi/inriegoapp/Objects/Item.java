package com.example.santi.inriegoapp.Objects;

import java.util.Date;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class Item {
    private Date Fecha;
    private int mm;
    private int estado;

    public Item(Date fecha, int mm, int estado) {
        Fecha = fecha;
        this.mm = mm;
        this.estado = estado;
    }

    public Item() {
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
