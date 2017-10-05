package com.example.santi.inriegoapp.Objects;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class Establecimiento {
    private int Id;
    private String Nombre;


    public Establecimiento() {
    }

    public Establecimiento(String nombre) {
        Nombre = nombre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

   public void JsonParser(JSONObject js) throws JSONException {
        this.setNombre(js.get("Description").toString());
        this.setId(js.getInt("FarmId"));
    }

}
