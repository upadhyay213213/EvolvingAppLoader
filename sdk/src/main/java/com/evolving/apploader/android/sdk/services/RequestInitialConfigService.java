package com.evolving.apploader.android.sdk.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.evolving.apploader.android.sdk.AppLoaderManager;
import com.evolving.apploader.android.sdk.api.GetConfigResponse;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;
import com.evolving.apploader.android.sdk.util.SharedPreferenceUtil;

/**
 * Created by nupadhay on 3/22/2016.
 */
public class RequestInitialConfigService extends Service {

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
        requestInitialConfig();
        return super.onStartCommand(pIntent, flags, startId);
    }

    private void requestInitialConfig() {
        String iccid = AppLoaderUtil.getICCID(mContext);
        String imei = AppLoaderUtil.getIMEI(mContext);
        AppLoaderManager.requestConfig(iccid, imei, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                if (!(o instanceof GetConfigResponse)) return; // throw error?
                GetConfigResponse response = (GetConfigResponse) o;
                SharedPreferenceUtil.setAdDuration(mContext, response.getAppAdDisplayDuration());
                SharedPreferenceUtil.setAppBaseUrl(mContext, response.getBaseUrl());
                SharedPreferenceUtil.setGCMTopic(mContext, response.getGCMTopic());
                SharedPreferenceUtil.setProjectId(mContext, response.getProjectId());
                // todo Stop and destroy the service
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Todo @task for Nandan retry policy 3 times else stop and destroy service notify user.
            }
        });
    }

    private void requestAppList() {
        /**
         * todo
         * Request provision list of apps and update shared preferences
         *
         */
    }
}
