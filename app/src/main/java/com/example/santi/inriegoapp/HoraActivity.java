package com.example.santi.inriegoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HoraActivity extends AppCompatActivity {
Toolbar t;
    Button bt;
    TimePicker tmp;
   int hora,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hora);
        t= (Toolbar) findViewById(R.id.toolbar_Pivot);
        setSupportActionBar(t);
          SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

   hora=sh.getInt("hora",20);
        min=sh.getInt("min",30);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
tmp= (TimePicker) findViewById(R.id.tmp);

           tmp.setCurrentHour(hora);

           tmp.setCurrentMinute(min);
        bt= (Button) findViewById(R.id.confirmar_hora);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(HoraActivity.this,Servicio.class));
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed= sh.edit();
                    ed.putInt("hora",tmp.getCurrentHour());
                ed.putInt("min",tmp.getCurrentMinute());
ed.commit();
ed.apply();

                if(ed.commit()) {
                    startService(new Intent(HoraActivity.this, Servicio.class).putExtra("hora", hora).putExtra("min", min));
                    onBackPressed();
                }
            }
        });
    }
}
