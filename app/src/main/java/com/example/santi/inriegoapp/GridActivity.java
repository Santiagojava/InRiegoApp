package com.example.santi.inriegoapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santi.inriegoapp.Adapters.ItemAdapter;
import com.example.santi.inriegoapp.Objects.Item;
import com.example.santi.inriegoapp.Objects.Pivot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by santi on 12/10/2017.
 */

public class GridActivity extends Activity {
    ArrayList<Item> l = new ArrayList<Item>();
    Typeface face;
    TextView title;
    String farmid = "";
    String token = "";
    int posicion = 0;
    String nom_pivot = "";
    String crop;
    String fenology;
    int kc;
    TextView fn,c,fc;
    String fecha_cul;
    ArrayList<String> Str = new ArrayList<>();
    private GridView gv;
    Button agregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        farmid = getIntent().getStringExtra("farmid");
        token = getIntent().getStringExtra("token");
        nom_pivot = getIntent().getStringExtra("nom_pivot");
        //Crear adapter
        gv =(GridView)findViewById(R.id.gridview);
        agregar = (Button)findViewById(R.id.agregar_riego);
        agregar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
        }
        );

        new Grid().execute(token,farmid,nom_pivot);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //Log.v("TAG", "CLICKED row number: " + arg2);

            }

        });
    }



    public class Grid extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String res = "";
            try {

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

            if (res == "")
                Toast.makeText(GridActivity.this, "Datos no validos", Toast.LENGTH_LONG).show();
            else {
                try {
                    JSONObject js = new JSONObject(res);
                    if (js.get("IsOk").toString() == "true") {
                        JSONObject farm = js.getJSONObject("Data");
                        JSONArray pivots = farm.getJSONArray("IrrigationRows");

                        for(int i=0;i<pivots.length();i++){
                            if(pivots.getJSONObject(i).get("Name").toString().equals(params[2])){
                                JSONObject pivot = pivots.getJSONObject(i);
                                fenology = pivot.get("Phenology").toString();
                                crop = pivot.get("Crop").toString();
                                kc = pivot.getInt("Kc");
                                String Str = pivot.getString("HarvestDate");
                                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                Date fcul = sfd.parse(Str);
                                Calendar hd = Calendar.getInstance();
                                hd.setTime(fcul);
                                fecha_cul = String.valueOf(hd.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(hd.get(Calendar.MONTH)) + "/" + String.valueOf(hd.get(Calendar.YEAR));
                                JSONArray advices = pivot.getJSONArray("Advices");
                                int cant = 0;
                                int cont = 0;
                                Date hoy = new Date();
                                Calendar inicio = Calendar.getInstance();
                                Calendar full = Calendar .getInstance();
                                full.setTime(hoy);
                                full.add(Calendar.DAY_OF_YEAR,-2);
                                inicio.setTime(hoy);
                                inicio.add(Calendar.DAY_OF_YEAR,-2);
                                //Calendar prueba= inicio;
                                for(int x=0;x<8;x++){
                                    Item it = new Item();
                                    Date deit = full.getTime();
                                    it.setFecha(deit);
                                    l.add(it);
                                    full.add(Calendar.DAY_OF_MONTH,1);
                                }

                                for(int x=0;x<advices.length();x++){
                                    /*Item itm = new Item();
                                    JSONObject advs = advices.getJSONObject(x);
                                    itm.JsonParser(advs);
                                    l.add(itm);*/
                                    JSONObject advs = advices.getJSONObject(x);
                                    String dateStr = advs.getString("Date");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                    Date date = sdf.parse(dateStr);
                                    Calendar fch = Calendar.getInstance();
                                    fch.setTime(date);
                                    if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                        l.get(0).JsonParser(advs);
                                        //inicio.add(Calendar.DAY_OF_YEAR,1);
                                    }
                                    else{
                                        inicio.add(Calendar.DAY_OF_YEAR,1);
                                        if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                            l.get(1).JsonParser(advs);
                                            //inicio.add(Calendar.DAY_OF_YEAR,1);
                                        }
                                        else{
                                            inicio.add(Calendar.DAY_OF_YEAR,1);
                                            if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                                l.get(2).JsonParser(advs);
                                                //inicio.add(Calendar.DAY_OF_YEAR,1);
                                            }
                                            else{
                                                inicio.add(Calendar.DAY_OF_YEAR,1);
                                                if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                                    l.get(3).JsonParser(advs);
                                                    //inicio.add(Calendar.DAY_OF_YEAR,1);
                                                }
                                                else{
                                                    inicio.add(Calendar.DAY_OF_YEAR,1);
                                                    if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                                        l.get(4).JsonParser(advs);
                                                        //inicio.add(Calendar.DAY_OF_YEAR,1);
                                                    }
                                                    else{
                                                        inicio.add(Calendar.DAY_OF_YEAR,1);
                                                        if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                                            l.get(5).JsonParser(advs);
                                                            //inicio.add(Calendar.DAY_OF_YEAR,1);
                                                        }
                                                        else{
                                                            inicio.add(Calendar.DAY_OF_YEAR,1);
                                                            if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                                                l.get(6).JsonParser(advs);
                                                                //inicio.add(Calendar.DAY_OF_YEAR,1);
                                                            }
                                                            else{
                                                                inicio.add(Calendar.DAY_OF_YEAR,1);
                                                                if(inicio.get(Calendar.DAY_OF_MONTH) == fch.get(Calendar.DAY_OF_MONTH)){
                                                                    l.get(7).JsonParser(advs);
                                                                    //inicio.add(Calendar.DAY_OF_YEAR,1);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                /*
                                while(cant < 8 && cont < advices.length()){
                                    JSONObject advs = advices.getJSONObject(cont);
                                    String dateStr = advs.getString("Date");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                                    Date date = sdf.parse(dateStr);


                                    if(date.getYear() != hoy.getYear() && date.getMonth() != hoy.getMonth() && date.getDay() != hoy.getDay()) {
                                        if (date.before(hoy)) {//Antes de hoy
                                            long diff = hoy.getTime() - date.getTime();
                                            int numero = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                            Item itm = new Item();
                                            itm.JsonParser(advs);
                                            l.add(itm);
                                            for(int x=0;x<numero;x++){
                                                Item it = new Item();
                                                l.add(it);
                                                cant++;
                                            }
                                        }
                                        else{//Despues de hoy
                                            Item itm = new Item();
                                            itm.JsonParser(advs);
                                            l.add(itm);
                                            cant ++;
                                            cont ++;
                                        }
                                    }
                                    else{//Igual hoy
                                        if(cant < 2 ){
                                            long diff = hoy.getTime() - date.getTime();
                                            int numero = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                            for(int x=0;x<numero;x++){
                                                Item it = new Item();
                                                l.add(it);
                                                cant++;
                                            }
                                        }
                                        Item itm = new Item();
                                        itm.JsonParser(advs);
                                        l.add(itm);
                                        cant ++;
                                        cont ++;
                                    }

                                }
                                */



                            }
                        }
                    } else {
                        Toast.makeText(GridActivity.this, "Error al cargar pivots, vuelva a intentar mas tarde", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            ItemAdapter ia = new ItemAdapter(GridActivity.this,l,face);
            gv.setAdapter(ia);
            gv.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return event.getAction() == MotionEvent.ACTION_MOVE;
                }

            });
            c = (TextView)findViewById(R.id.tipocultivo);
            fc = (TextView)findViewById(R.id.fecha_c);
            fn = (TextView)findViewById(R.id.fenologia);
            c.setText(crop);
            c.setTypeface(null, Typeface.BOLD);
            fc.setText(fecha_cul);
            fc.setTypeface(null, Typeface.BOLD);
            fn.setText(fenology);
            fn.setTypeface(null, Typeface.BOLD);
        }
    }
}
