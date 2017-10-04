package com.example.santi.inriegoapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.santi.inriegoapp.Objects.Establecimiento;
import com.example.santi.inriegoapp.R;

import java.util.ArrayList;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class EstablecimientoAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Establecimiento> Establecimientos;
    protected Typeface face;
    public EstablecimientoAdapter(Activity activity, ArrayList<Establecimiento> establecimientos,Typeface face) {
        this.activity = activity;
        Establecimientos = establecimientos;
        this.face=face;

    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Establecimiento> getEstablecimientos() {
        return Establecimientos;
    }

    public void setEstablecimientos(ArrayList<Establecimiento> establecimientos) {
        Establecimientos = establecimientos;
    }

    @Override
    public int getCount() {
        return Establecimientos.size();
    }

    @Override
    public Object getItem(int position) {
        return Establecimientos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     View v=convertView;


        if(convertView==null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_est, null);

        }
        Establecimiento dir = Establecimientos.get(position);

        TextView title = (TextView) v.findViewById(R.id.est_nombre);
        title.setTypeface(face);
        title.setText(dir.getNombre());

        return v;

    }





}
