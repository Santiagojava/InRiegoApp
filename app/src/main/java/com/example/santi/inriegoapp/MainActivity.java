package com.example.santi.inriegoapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button entrar;
    Context thiscontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thiscontext = this;
        //PRUEBA DE SERVICIO BACKGROUND
        //startService(new Intent(thiscontext,SegundoPlano.class));
        entrar=(Button)findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BaseSQLiteHelper dbbase = new BaseSQLiteHelper(MainActivity.this,"DBSync",null, 1);
                SQLiteDatabase db = dbbase.getWritableDatabase();
                String s = "Primer Prueba";
                db.execSQL("INSERT INTO DatosJson (json) VALUES('"+ s +"')");
                db.close();
                Toast.makeText(MainActivity.this,"Se insertado correctamente en la base",Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(MainActivity.this, EstablecimientoActivity.class);
                startActivity(intent);*/

                //PRUEBA DE SERVICIO BACKGROUND
                //stopService(new Intent(thiscontext,SegundoPlano.class));
            }
        });;
    }
}
