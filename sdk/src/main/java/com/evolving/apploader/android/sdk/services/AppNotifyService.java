package com.evolving.apploader.android.sdk.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.evolving.apploader.android.sdk.AppLoaderManager;

/**
 * Created by nupadhay on 3/22/2016.
 */
public class AppNotifyService extends Service {

    private Context mContext;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        mContext = this;
        Toast.makeText(this, "NotifyingDailyService", Toast.LENGTH_LONG).show();
        Log.i("bootbroadcastpoc", "RequestInitialConfigService");
        AppLoaderManager.init(mContext);
        String packageName = pIntent.getStringExtra("EXTRA_PACKAGE_NAME");
        notifyServer(packageName);
        return super.onStartCommand(pIntent, flags, startId);
    }

    private void notifyServer(String packageName) {
        //todo
        /**
         * Get list of apps from preference
         * Match for package name
         * If found call api notify app complete request
         * Remove from local list if app found.
         * stop the service
         */
    }

}
