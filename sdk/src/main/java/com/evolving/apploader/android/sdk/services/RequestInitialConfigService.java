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
import com.evolving.apploader.android.sdk.api.ProvisionalOfferResponse;
import com.evolving.apploader.android.sdk.api.ProvisionalOfferResponseOne;
import com.evolving.apploader.android.sdk.database.DataBaseQuery;
import com.evolving.apploader.android.sdk.model.ProvisionalOffer;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;
import com.evolving.apploader.android.sdk.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by nupadhay on 3/22/2016.
 */
public class RequestInitialConfigService extends Service {

    private static int sRetries = 3;
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
//        AppLoaderManager.init(mContext, null);
        boolean repeat = pIntent.getBooleanExtra("REPEAT", false);
        if (repeat) {
            requestAppList();
        } else {
            requestInitialConfig();
        }
        return super.onStartCommand(pIntent, flags, startId);
    }

    private void requestInitialConfig() {
        String iccid = AppLoaderUtil.getICCID(mContext);
        String imei = AppLoaderUtil.getIMEI(mContext);
        AppLoaderManager.requestConfig(iccid, imei, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                if (!(o instanceof GetConfigResponse)) {
                    if (sRetries > 0) {
                        sRetries--;
                        requestInitialConfig();
                    } else {
                        stopSelf();
                    }
                    return;
                }
                GetConfigResponse response = (GetConfigResponse) o;
                SharedPreferenceUtil.setAdDuration(mContext, response.getAppAdDisplayDuration());
                SharedPreferenceUtil.setAppBaseUrl(mContext, response.getBaseUrl());
                SharedPreferenceUtil.setGCMTopic(mContext, response.getGCMTopic());
                SharedPreferenceUtil.setProjectId(mContext, response.getProjectId());
                if (SharedPreferenceUtil.isFirstLaunch(mContext)) {
                    SharedPreferenceUtil.setIsFirstLaunch(mContext, false);
                }
                requestAppList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (sRetries > 0) {
                    sRetries--;
                    requestInitialConfig();
                } else {
                    stopSelf();
                }
            }
        });
    }

    private void requestAppList() {
        String iccid = AppLoaderUtil.getICCID(mContext);
        String imei = AppLoaderUtil.getIMEI(mContext);
        AppLoaderManager.provisionalOfferRequest(iccid, imei, new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                if (!(o instanceof ProvisionalOfferResponseOne)) {
                    if (sRetries > 0) {
                        sRetries--;
                        requestAppList();
                    } else {
                        stopSelf();
                    }
                    return; // throw error?
                }
                ProvisionalOfferResponseOne response = (ProvisionalOfferResponseOne) o;
                //TODO how to save data in preferces as it wnt be just one package
                Set<String> mySet = new HashSet<>();
                ArrayList<ProvisionalOfferResponse> mOfferList =response.getmProvisionalOffer();
                for(int i=0; i<mOfferList.size();i++ ){
                    ProvisionalOffer mProvisionalOffer = new ProvisionalOffer();
                    mProvisionalOffer.setmType(mOfferList.get(i).getmType());
                    mProvisionalOffer.setmUrl(mOfferList.get(i).getmURL());
                    mProvisionalOffer.setmPackage(mOfferList.get(i).getmPackage());
                    mProvisionalOffer.setmIconUrl(mOfferList.get(i).getmIconUrl());
                    mProvisionalOffer.setmDescription("");
                    mProvisionalOffer.setmRating(mOfferList.get(i).getmRating());
                    mProvisionalOffer.setmDeveloper(mOfferList.get(i).getmDeveloper());
                    mProvisionalOffer.setmLabel("");
                    mySet.add(response.getmProvisionalOffer().get(i).getmPackage());
                    DataBaseQuery.addProductToDataBase(mProvisionalOffer, mContext);
                }
                SharedPreferenceUtil.setAppPackageName(mContext, mySet);
                stopSelf();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (sRetries > 0) {
                    sRetries--;
                    requestAppList();
                } else {
                    stopSelf();
                }
            }
        });
    }
}
