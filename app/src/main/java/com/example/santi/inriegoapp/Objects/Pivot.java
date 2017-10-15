package com.example.santi.inriegoapp.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class Pivot implements Serializable{
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

    public void JsonParser(JSONObject js) throws JSONException {
        this.setNombre(js.get("Name").toString());
        this.setFenologia(js.get("Phenology").toString());
        this.setFecha(new Date());
    }
}
