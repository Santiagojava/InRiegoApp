package com.example.santi.inriegoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santi.inriegoapp.Adapters.EstablecimientoAdapter;
import com.example.santi.inriegoapp.Adapters.PivotAdapter;
import com.example.santi.inriegoapp.Objects.Pivot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by santi on 8/10/2017.
 */

public class PivotActivity extends Activity {
    Pivot a = new Pivot();

    ArrayList<Pivot> l = new ArrayList<Pivot>();
    Typeface face;
    TextView title;
    private ListView list;
    String farmid = "";
    String token = "";
    String nom_pivot = "";
    ArrayList<String> Str = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pivot);
        face= Typeface.createFromAsset(getAssets(),"Raleway-Light.ttf");
        farmid = getIntent().getStringExtra("farmid");
        token = getIntent().getStringExtra("token");
        Str=getIntent().getBundleExtra("extra").getStringArrayList("pivots");
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
            Pivot piv = (Pivot)list.getAdapter().getItem(arg2);
            nom_pivot = piv.getNombre();
            Intent hola = new Intent(getApplicationContext(), GridActivity.class);
            Bundle  a =new Bundle();
            hola.putExtra("extra",a);
            hola.putExtra("token",token);
            hola.putExtra("farmid",farmid);
            hola.putExtra("nom_pivot",nom_pivot);
            startActivity(hola);
            }

        });
    }
}
