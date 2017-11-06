package com.example.santi.inriegoapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;
import com.example.santi.inriegoapp.sqlite.BD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Servicio extends Service {
    private NotificationManager nm;
    private static final int ID_NOTIFICACION_CREAR = 1;
    boolean flag = false;
    int hora=0;
    int min=0;

    public Servicio() {
    }

    private static final String TAG = "EjemploServicio";
    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.d(TAG, "El servicio se ha creado");
        timer = new Timer();
        SharedPreferences sh =   PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        hora=sh.getInt("hora",0);
        min=sh.getInt("min",29);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        java.util.Date DAT =new java.util.Date();

       Calendar c= new GregorianCalendar();
        c.setTimeInMillis(DAT.getTime());
        c.set(Calendar.HOUR_OF_DAY,hora);
        c.set(Calendar.MINUTE,min);
        long d=0;
        Log.d(TAG, "onStartCommand: "+DAT);
        Log.d(TAG, "onStartCommand: "+c.getTime());
        if(DAT.getTime()<c.getTime().getTime())
                d=c.getTime().getTime()-DAT.getTime();
            else {
            c.add(Calendar.DATE, 1);
            d = c.getTime().getTime()- DAT.getTime();
        }

        int icono = R.mipmap.ic_launcher;
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);


        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(d);
        Log.d(TAG, String.valueOf(d));
        long segundos = d / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;
        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(icono)
                .setContentTitle("Sincronizacion")
                .setContentText("Se sincronizara en " + horas +" Horas y "+minutos%60+ " Minutos")
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(false);

        mNotifyMgr.notify(1, mBuilder.build());
        Timer a = new Timer();
        a.schedule(new TimerTask() {
                       @Override
                       public void run() {
                           EjecutarTarea();
                       }
                   }

                , d, 86400000);


        return flags;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Finalizando la tarea del servicio");

    }

    public void EjecutarTarea() {
        Thread t = new Thread(new Runnable() {
            public void run() {


                int icono = R.mipmap.ic_launcher;
                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                Intent i = new Intent(Servicio.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(Servicio.this, 0, i, 0);

                mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(icono)
                        .setContentTitle("Sincronizacion")
                        .setContentText("EL SERVICIO ESTA CORRIENDO!")
                        .setVibrate(new long[]{100, 250, 100, 500})
                        .setAutoCancel(false);

                mNotifyMgr.notify(1, mBuilder.build());
new Sincro().execute();
            }
        });
        t.start();
    }


    public class Sincro extends AsyncTask<String, Void, Boolean> {
String res ="";
        @Override
        protected Boolean doInBackground(String... params) {
            String token="";


            try {

                BD db1 = new BD(getApplicationContext(), " inriego.db", null, 1);
                SQLiteDatabase db = db1.getWritableDatabase();
                ContentValues datos = new ContentValues();
for (int i=0;i<3;i++) {
    Cursor c = db.rawQuery("SELECT * FROM INGRESOS", null);
flag = false;
    if (c.moveToFirst()) {
        do {
            JSONObject reg = new JSONObject();
            String js = c.getString(0);
            try {
                reg = new JSONObject(js);
                URL url;
                if (reg.get("TIPO").toString()== "1")
                    url = new URL("http://iradvisor.pgwwater.com.uy:9080/api/IrrigationData/AddIrrigation");
                else
                    url = new URL("http://iradvisor.pgwwater.com.uy:9080/api/IrrigationData/AddRain");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;");


                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(String.valueOf(reg));
                out.close();
                int responseCode = conn.getResponseCode();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                res = response.toString();
                JSONObject r = new JSONObject(res);
                if (r.get("IsOk").toString() == "true")
                    flag = true;
                else
                    flag = false;


            } catch (JSONException e) {
                flag = false;
            }
        }
        while (flag & c.moveToNext());
if(flag=true)
        i=3;
        else
    Thread.sleep(900000);
    }
}

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return flag;
        }

        @Override
        protected void onPostExecute(Boolean s) {
          if(s){
              int icono = R.mipmap.ic_launcher;
              NotificationCompat.Builder mBuilder;
              NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

              mBuilder = new NotificationCompat.Builder(getApplicationContext())
                      .setSmallIcon(icono)
                      .setContentTitle("Sincronizacion")
                      .setContentText("Se ah Sincronizado")
                      .setVibrate(new long[]{100, 250, 100, 500})
                      .setAutoCancel(false);

              mNotifyMgr.notify(1, mBuilder.build());
          }
          else
            {
                int icono = R.mipmap.ic_launcher;
                NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(icono)
                        .setContentTitle("Sincronizacion")
                        .setContentText("Error al sincronizar")
                        .setVibrate(new long[]{100, 250, 100, 500})
                        .setAutoCancel(false);

                mNotifyMgr.notify(1, mBuilder.build());

            }
        }
    }
}