package com.evolving.apploader.android.sdk.pushnotification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.evolving.apploader.android.sdk.AppLoaderManager;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;
import com.google.android.gms.gcm.GcmListenerService;


public class PushNotificationService extends GcmListenerService{


    @Override
    public void onMessageReceived(String from, Bundle data) {

        String dataMessage = data.toString().substring(7);
        String dataMessage1 = dataMessage.substring(0,dataMessage.length()-1);
        AppLoaderManager.getGCMMessage(dataMessage1);
    }
}
