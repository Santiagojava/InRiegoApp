package com.example.santi.inriegoapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.santi.inriegoapp.Objects.Pivot;
import com.example.santi.inriegoapp.R;

import java.util.ArrayList;

/**
 * Created by KevinQ on 24/9/2017.
 */

public class PivotAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Pivot> Pivots;
    protected Typeface face;

    public PivotAdapter(Activity activity, ArrayList<Pivot> pivots, Typeface face) {
        this.activity = activity;
        Pivots = pivots;
        this.face = face;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Pivot> getPivots() {
        return Pivots;
    }

    public void setPivots(ArrayList<Pivot> pivots) {
        Pivots = pivots;
    }

    @Override
    public int getCount() {
        return Pivots.size();
    }

    @Override
    public Object getItem(int position) {
        return Pivots.get(position);
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
            v = inf.inflate(R.layout.item_piv, null);

        }
        Pivot dir = Pivots.get(position);

        TextView title = (TextView) v.findViewById(R.id.pivot_nom);
        title.setTypeface(face);
        title.setText(dir.getNombre());

        return v;
    }


}
