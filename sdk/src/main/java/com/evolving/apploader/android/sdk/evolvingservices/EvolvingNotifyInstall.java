package com.evolving.apploader.android.sdk.evolvingservices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nupadhay on 3/22/2016.
 */
public class EvolvingNotifyInstall extends Service{

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        Toast.makeText(this, "NotifyingDailyService", Toast.LENGTH_LONG).show();
        Log.i("bootbroadcastpoc", "EvolvingNotifyInstall");
        return super.onStartCommand(pIntent, flags, startId);
    }
}
