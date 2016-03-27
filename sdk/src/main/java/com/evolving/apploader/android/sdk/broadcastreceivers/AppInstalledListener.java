package com.evolving.apploader.android.sdk.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.evolving.apploader.android.sdk.services.AppNotifyService;

import java.util.List;


public class AppInstalledListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //TODO
        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
            Intent serviceIntent = new Intent(context, AppNotifyService.class);
            serviceIntent.putExtra("EXTRA_PACKAGE_NAME", intent.getData().toString());
            context.startService(serviceIntent);
        }
        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
            System.out.println("INReciverRemoved" + intent.getData().toString());
        }
        if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
            Intent serviceIntent = new Intent(context, AppNotifyService.class);
            serviceIntent.putExtra("EXTRA_PACKAGE_NAME", intent.getData().toString());
            context.startService(serviceIntent);
        }
    }
}
