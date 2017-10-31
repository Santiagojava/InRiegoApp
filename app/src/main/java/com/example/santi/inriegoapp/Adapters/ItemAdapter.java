package com.example.santi.inriegoapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.santi.inriegoapp.Objects.Item;
import com.example.santi.inriegoapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        //Prueba

        int cellWidth = 40;
        int cellHeight = 50;
        //

        if(convertView==null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_tabla, null);

        }

        v.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 200));

        Item it = Items.get(position);
        TextView cant = (TextView) v.findViewById(R.id.cantidad_grid);
        TextView dia = (TextView) v.findViewById(R.id.dia_grid);
        ImageView imagen = (ImageView)v.findViewById(R.id.riego_imagen);
        //if(!it.getTipo().equals("NULO")) {
            if(it.getMm() != 0){
                cant.setText(String.valueOf(it.getMm()));
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(it.getFecha());
            dia.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            if (it.getTipo().equals("Rain")) {
                imagen.setImageResource(R.drawable.lluvia_imagen);
            } else {
                if (it.getTipo().equals("Irrigation")) {
                    imagen.setImageResource(R.drawable.riego_imagen);
                }
            }
        //}
        

        return v;




    }
}
