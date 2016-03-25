package com.evolving.apploader.android.sdk.notifyinstall;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.List;


public class AppInstalledListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String action = intent.getAction();
        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
            String appNmae = getRecentInstallPackageName(intent,context);
            Log.v("appNmae:", appNmae);
        }
        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
            Log.v("DATA:", intent.getData().toString());
        }
        if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
            Log.v("DATA:", intent.getData().toString());
        }
    }

    private String getRecentInstallPackageName(Intent intent, Context context) {
        String appName="";
        String[] a = intent.getData().toString().split(":");
        String packageName = a[a.length - 1];
        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo packageInfo = packageInfoList.get(i);
            if (packageInfo.packageName.equals(packageName)) {
                appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            }
        }
        return appName;
    }

//try this if above method did not work.
    private String getInstalledPackageInfo(Context context) {
        final PackageManager pm = context.getApplicationContext().getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");
        return applicationName;
    }
}
