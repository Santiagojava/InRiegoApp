package com.example.santi.inriegoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.santi.inriegoapp.Adapters.EstablecimientoAdapter;
import com.example.santi.inriegoapp.Adapters.PivotAdapter;
import com.example.santi.inriegoapp.Objects.Pivot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by santi on 8/10/2017.
 */

public class PivotActivity extends AppCompatActivity {
    Pivot a = new Pivot();
String token="";
    ArrayList<Pivot> l = new ArrayList<Pivot>();
    Typeface face;
    TextView title;
    private ListView list;
    ArrayList<String> Str = new ArrayList<>();
Button bt;
    Toolbar t;
   String Nombre="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pivot);
        face= Typeface.createFromAsset(getAssets(),"Raleway-Light.ttf");
        token=getIntent().getStringExtra("token");
        Str=getIntent().getBundleExtra("extra").getStringArrayList("pivots");
        Nombre=getIntent().getStringExtra("Establecimiento");
        for (String f:Str) {
            Pivot p=new Pivot();
            try {
                p.JsonParser(new JSONObject(f));
                l.add(p);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        PivotAdapter ad=new PivotAdapter(this,l,face);
        face=Typeface.createFromAsset(getAssets(),"Lato-Semibold.ttf");
        title= (TextView) findViewById(R.id.textView2);
        title.setTypeface(face);
        list =(ListView)findViewById(R.id.listapiv);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
            // TODO Auto-generated method stub
            //Log.v("TAG", "CLICKED row number: " + arg2);

            }

        });
        bt= (Button) findViewById(R.id.bt_Agregar_lluvia);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(PivotActivity.this ,AgregarLluviaActivity.class );
               i.putExtra("pivots",l);
                i.putExtra("token",token);
                startActivity(i);
            }
        });
        t= (Toolbar) findViewById(R.id.toolbar_pivots);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        t.setTitle(Nombre);
    }
}
