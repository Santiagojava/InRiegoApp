 package com.example.santi.inriegoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.santi.inriegoapp.Objects.Establecimiento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    Button entrar;
    EditText User;
    EditText Pass;
    TextView Error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        String user=sh.getString("user","");
        String pass=sh.getString("pass","");
        if(user!="" && pass!=""){

            new Login().execute(user,pass);
        }
        setContentView(R.layout.activity_main);
        entrar=(Button)findViewById(R.id.entrar);

    User= (EditText) findViewById(R.id.nombre);
        Pass= (EditText) findViewById(R.id.contraseña);
        Error= (TextView) findViewById(R.id.Error);
        Typeface face = Typeface.createFromAsset(getAssets(), "Raleway-Medium.ttf");
    User.setTypeface(face);
        Pass.setTypeface(face);
        Error.setTypeface(face);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Login().execute(User.getText().toString(),Pass.getText().toString());
                 }
        });




    }
    public class Login extends AsyncTask<String, Void, String> {
        String user="";
        String pass="";
        @Override
        protected String doInBackground(String... params) {
            String res="";

            try {

                URL url = new URL("http://iradvisor.pgwwater.com.uy:9080/api/Auth/userName/"+params[0]+"/password/"+params[1]+"");
                user=params[0];
                pass=params[1];
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
            if(s=="")
                Error.setText("Datos no validos");
            else {
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.get("IsOk").toString() == "true") {
                        ArrayList <Establecimiento> est=new ArrayList<>();
                        JSONObject farm=js.getJSONObject("Data");
                        JSONArray farms=farm.getJSONArray("Farms");

                        Bundle  a =new Bundle();
                        ArrayList<String> farmsS = new ArrayList<>();
                        ArrayList<String> ids = new ArrayList<>();
                        for(int i=0;i<farms.length();i++){
                           farmsS.add(farms.get(i).toString());
                        }
                        a.putStringArrayList("farms",farmsS);
SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


                        String token=farm.getString("Token");
                        SharedPreferences.Editor ed= sh.edit();
                        ed.putString("token",token);
                        ed.putString("user",user);
                        ed.putString("pass",pass);
                        ed.commit();
                        Intent hola = new Intent(getApplicationContext(), EstablecimientoActivity.class);
                        hola.putExtra("extra",a);
                        hola.putExtra("token",farm.getString("Token"));
                        startActivity(hola);
                    } else {
                        Error.setText("Datos Incorrectos");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
