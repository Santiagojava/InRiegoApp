package com.example.santi.inriegoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    Button entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrar=(Button)findViewById(R.id.entrar);
        Properties properties = new Properties();;

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

        NotificationManager mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificacion_id = 2345;
        int icon = R.drawable.ic_not;
        CharSequence tickerText = "Notification Bar";
        long when = System.currentTimeMillis();
        Notification notification =mBuilder.build();
        mNotificationManager.notify(notificacion_id,notification);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hola = new Intent(getApplicationContext(), EstablecimientoActivity.class);
                startActivity(hola);
            }
        });;
    }
}
