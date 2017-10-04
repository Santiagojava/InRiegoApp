package com.example.santi.inriegoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    Button entrar;
    EditText User;
    EditText Pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrar=(Button)findViewById(R.id.entrar);
        Properties properties = new Properties();;
    User= (EditText) findViewById(R.id.nombre);
        Pass= (EditText) findViewById(R.id.contraseña);

        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("propiedades");
            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
      String hora=  properties.getProperty("hora");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_not)
                        .setContentTitle("Mi Aplicacion")
                        .setContentText(hora);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Login().execute(User.getText().toString(),Pass.getText().toString());
                 }
        });;
    }
    public class Login extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String res="";
            try {

                URL url = new URL("http://iradvisor.pgwwater.com.uy:9080/api/Auth/userName/"+params[0]+"/password/"+params[1]+"");
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
                Toast.makeText(MainActivity.this, "Datos no validos", Toast.LENGTH_LONG).show();
            else {
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.get("IsOk").toString() == "true") {
                        Intent hola = new Intent(getApplicationContext(), EstablecimientoActivity.class);
                        startActivity(hola);
                    } else {
                        Toast.makeText(MainActivity.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
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
