package com.example.santi.inriegoapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santi.inriegoapp.Adapters.EstablecimientoAdapter;
import com.example.santi.inriegoapp.Objects.Establecimiento;
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
import java.util.List;

/**
 * Created by santi on 29/8/2017.
 */

public class EstablecimientoActivity extends Activity {
Establecimiento a=new Establecimiento("San Jose");
    Establecimiento b=new Establecimiento("Colonia");
    ArrayList<Establecimiento> l=new ArrayList<Establecimiento>();
    private ListView list;
    Typeface face;
    TextView title;
    ArrayList<String> Str=new ArrayList<>();
    String token = "";
    String farmid = "";
    String Nombre="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.establecimiento);
   face= Typeface.createFromAsset(getAssets(),"Raleway-Light.ttf");
        farmid = getIntent().getStringExtra("farmid");
        token = getIntent().getStringExtra("token");
Str=getIntent().getBundleExtra("extra").getStringArrayList("farms");
        for (String f:Str) {
            Establecimiento e=new Establecimiento();
            try {
            e.JsonParser(new JSONObject(f));
                l.add(e);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        EstablecimientoAdapter ad=new EstablecimientoAdapter(this,l,face);
        face=Typeface.createFromAsset(getAssets(),"Lato-Semibold.ttf");
        title= (TextView) findViewById(R.id.Title_Establecimiento);
        title.setTypeface(face);
        list =(ListView)findViewById(R.id.list_esta);
        list.setAdapter(ad);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "CLICKED row number: " + position);
                Establecimiento seleccionado = (Establecimiento)list.getAdapter().getItem(position);
                farmid = String.valueOf(seleccionado.getId());
              Nombre=seleccionado.getNombre();
                new Pivots().execute(token,farmid,Nombre);
            }



        });

    }





    public class Pivots extends AsyncTask<String, Void, String> {
String Nombre="";
        @Override
        protected String doInBackground(String... params) {
            String res = "";
            try {
Nombre=params[2];
                URL url = new URL("http://iradvisor.pgwwater.com.uy:9080/api/IrrigationData/token/" + params[0] + "/farmId/" + params[1] + "");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                res = response.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return res;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == "")
                Toast.makeText(EstablecimientoActivity.this, "Datos no validos", Toast.LENGTH_LONG).show();
            else {
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.get("IsOk").toString() == "true") {
                        ArrayList<Establecimiento> est = new ArrayList<>();
                        JSONObject farm = js.getJSONObject("Data");
                        JSONArray pivots = farm.getJSONArray("IrrigationRows");

                        Bundle  a =new Bundle();
                        ArrayList<String> pivotsS = new ArrayList<>();
                        for(int i=0;i<pivots.length();i++){
                            pivotsS.add(pivots.get(i).toString());
                        }
                        a.putStringArrayList("pivots",pivotsS);

                        Intent hola = new Intent(getApplicationContext(), PivotActivity.class);
                        hola.putExtra("extra",a);
                        hola.putExtra("token",token);
                        hola.putExtra("farmid",farmid);
                        hola.putExtra("Establecimiento",Nombre);
                        startActivity(hola);

                    } else {
                        Toast.makeText(EstablecimientoActivity.this, "Error al cargar pivots, vuelva a intentar mas tarde", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
