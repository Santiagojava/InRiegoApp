package com.example.santi.inriegoapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.santi.inriegoapp.Adapters.EstablecimientoAdapter;
import com.example.santi.inriegoapp.Objects.Establecimiento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santi on 29/8/2017.
 */

public class EstablecimientoActivity extends Activity {
Establecimiento a=new Establecimiento("San Jose");
    Establecimiento b=new Establecimiento("Colonia");
    ArrayList<Establecimiento> l=new ArrayList<Establecimiento>();
    Typeface face;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.establecimiento);
   face= Typeface.createFromAsset(getAssets(),"Raleway-Light.ttf");

        l.add(a);
        l.add(b);
        EstablecimientoAdapter ad=new EstablecimientoAdapter(this,l,face);
        face=Typeface.createFromAsset(getAssets(),"Lato-Semibold.ttf");
        title= (TextView) findViewById(R.id.Title_Establecimiento);
        title.setTypeface(face);
        ListView list      =(ListView)findViewById(R.id.list_esta);
        list.setAdapter(ad);


    }
}

