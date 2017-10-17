package com.example.santi.inriegoapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santi.inriegoapp.Objects.Item;
import com.example.santi.inriegoapp.R;

import java.util.ArrayList;

/**
 * Created by KevinQ on 26/9/2017.
 */

public class ItemAdapter    extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Item> Items;
    protected Typeface face;

    public ItemAdapter(Activity activity, ArrayList<Item> items,Typeface face) {
        this.activity = activity;
        this.Items = items;
        this.face=face;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Item> items) {
        Items = items;
    }

    public ItemAdapter() {
    }

    @Override
    public int getCount() {return Items.size();}

    @Override
    public Object getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;


        if(convertView==null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_tabla, null);

        }

        Item it = Items.get(position);
        TextView cant = (TextView) v.findViewById(R.id.cantidad_grid);
        TextView dia = (TextView) v.findViewById(R.id.dia_grid);
        ImageView imagen = (ImageView)v.findViewById(R.id.riego_imagen);
        if(!it.getTipo().equals("NULO")) {
            cant.setText(String.valueOf(it.getMm()));
            dia.setText(String.valueOf(it.getFecha().getDay()));
            if (it.getTipo().equals("Rain")) {
                imagen.setImageResource(R.drawable.lluvia_imagen);
            } else {
                if (it.getTipo().equals("Irrigation")) {
                    imagen.setImageResource(R.drawable.riego_imagen);
                }
            }
        }

        return v;




    }
}
