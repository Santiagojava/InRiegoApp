package com.example.santi.inriegoapp.Objects;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class Establecimiento {
    private String Nombre;


    public Establecimiento() {
    }

    public Establecimiento(String nombre) {
        Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
