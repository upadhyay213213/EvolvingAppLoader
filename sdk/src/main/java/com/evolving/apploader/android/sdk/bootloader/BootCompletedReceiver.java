package com.evolving.apploader.android.sdk.bootloader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evolving.apploader.android.sdk.evolvingservices.EvolvingNotifyInstall;

/**
 * Created by nupadhay on 3/22/2016.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        Log.w("boot_broadcast_poc", "starting service...");
        context.startService(new Intent(context, EvolvingNotifyInstall.class));
    }
}
