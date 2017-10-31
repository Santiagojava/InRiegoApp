package com.example.santi.inriegoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by KevinQ on 2/10/2017.
 */

public class IniciadorServicio extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent servicio = new Intent(context,Servicio.class);
        context.startService(servicio);
    }
}
