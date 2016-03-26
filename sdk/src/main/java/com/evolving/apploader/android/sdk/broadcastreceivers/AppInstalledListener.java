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
        String packageName = "";
        //TODO
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
        Intent serviceIntent = new Intent(context, AppNotifyService.class);
        serviceIntent.putExtra("EXTRA_PACKAGE_NAME", packageName);
        context.startService(serviceIntent);

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
