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
import com.evolving.apploader.android.sdk.api.NotifyAppCompleteResponse;
import com.evolving.apploader.android.sdk.database.DataBaseQuery;
import com.evolving.apploader.android.sdk.model.ProvisionalOffer;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;

import java.util.ArrayList;


/**
 *
 * Created by nupadhay on 3/22/2016.
 */
public class AppNotifyService extends Service {
    private static int sRetries = 3;
    private Context mContext;
    private String packageName;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent pIntent, int flags, int startId) {
        mContext = this;
        Toast.makeText(this, "NotifyingDailyService", Toast.LENGTH_LONG).show();
        Log.i("bootbroadcastpoc", "RequestInitialConfigService");
        packageName = pIntent.getStringExtra("EXTRA_PACKAGE_NAME");
        notifyServer(mContext, packageName);
        return super.onStartCommand(pIntent, flags, startId);
    }

    private void notifyServer(Context ctx, String packageName) {
        //TODO @vipul pls check weather the retry policy is alryt or not let me know if you needed any changes
        ArrayList<ProvisionalOffer> mProvisionalOfferList = DataBaseQuery.getProvisionalOffer(ctx);
        if (packageName != null && mProvisionalOfferList != null) {
            for (int i = 0; i < mProvisionalOfferList.size(); i++) {

                if (mProvisionalOfferList.get(i).getmPackage().equalsIgnoreCase(packageName)) {
                    DataBaseQuery.deleteProductFromDatabase(mProvisionalOfferList.get(i).getmPackage(),ctx);
                    requestNotifyComplete(ctx, packageName);
                    break;
                } else {
                    continue;
                }
            }


        }
    }

    private void requestNotifyComplete(final Context ctx, final String packageName) {
        String iccid = AppLoaderUtil.getICCID(ctx);
        String imei = AppLoaderUtil.getIMEI(ctx);
        AppLoaderManager.notifyAppCompleteRequest(iccid, imei, packageName, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                if (!(o instanceof NotifyAppCompleteResponse)) {
                    if (sRetries > 0) {
                        sRetries--;
                        requestNotifyComplete(ctx, packageName);
                    } else {
                        stopSelf();
                    }
                    return;
                }
                NotifyAppCompleteResponse response = (NotifyAppCompleteResponse) o;
                stopSelf();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (sRetries > 0) {
                    sRetries--;
                    requestNotifyComplete(ctx,packageName);
                } else {
                    stopSelf();
                }
            }
        });
    }
}
