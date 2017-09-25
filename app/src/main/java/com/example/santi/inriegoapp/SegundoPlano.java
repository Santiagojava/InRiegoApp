package com.example.santi.inriegoapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by santi on 20/9/2017.
 */

public class SegundoPlano extends Service {
    private MediaPlayer mp;
    private Context thiscontext = this;
    @Override
    public void onCreate(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flag ,int idProcess ) {
        mp = MediaPlayer.create(thiscontext,R.raw.oasis);
        mp.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mp.stop();
    }
}
