package com.example.santi.inriegoapp.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class Item {
    private Date Fecha;
    private int mm;
    private int estado;
    private String tipo;

    public Item(Date fecha, int mm, int estado,String tipo) {
        Fecha = fecha;
        this.mm = mm;
        this.estado = estado;
        this.tipo=tipo;
    }

    public Item() {
        this.tipo="NULO";
    }

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

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

    public void JsonParser(JSONObject js) throws JSONException, ParseException {
        this.setTipo(js.get("IrrigationType").toString());
        this.setMm(js.getInt("Quantity"));
        String dateStr = js.getString("Date");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = sdf.parse(dateStr);
        this.setFecha(date);
    }
}
