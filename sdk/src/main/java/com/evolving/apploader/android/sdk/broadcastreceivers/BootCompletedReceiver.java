package com.evolving.apploader.android.sdk.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.evolving.apploader.android.sdk.services.RequestInitialConfigService;

/**
 * Created by nupadhay on 3/22/2016.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        Log.w("boot_broadcast_poc", "starting service...");
        context.startService(new Intent(context, RequestInitialConfigService.class));
    }
}
