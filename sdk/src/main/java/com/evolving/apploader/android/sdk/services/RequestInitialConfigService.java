package com.evolving.apploader.android.sdk.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
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
        Toast.makeText(this, "FirstTimeAppInsatlled and Boot Time", Toast.LENGTH_LONG).show();
        requestInitialConfig();
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

                //GCM topic please use error handler for the same in future use
                AppLoaderManager.registerGCM(mContext,response.getProjectId(),response.getGCMTopic());
                if (SharedPreferenceUtil.isFirstLaunch(mContext)) {
                    SharedPreferenceUtil.setIsFirstLaunch(mContext, false);
                    requestAppList();
                } else {
                    stopSelf();
                }
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
                for(int i=0; i<response.getmProvisionalOffer().size();i++ ){
                    ProvisionalOffer mProvisionalOffer = new ProvisionalOffer();
                    mProvisionalOffer.setmType(response.getmProvisionalOffer().get(i).getmType());
                    mProvisionalOffer.setmUrl(response.getmProvisionalOffer().get(i).getmURL());
                    mProvisionalOffer.setmPackage(response.getmProvisionalOffer().get(i).getmPackage());
                    mProvisionalOffer.setmIconUrl(response.getmProvisionalOffer().get(i).getmIconUrl());
                    mProvisionalOffer.setmDescription(response.getmProvisionalOffer().get(i).getmDescription());
                    mProvisionalOffer.setmRating(response.getmProvisionalOffer().get(i).getmRating());
                    mProvisionalOffer.setmDeveloper(response.getmProvisionalOffer().get(i).getmDeveloper());
                    mProvisionalOffer.setmLabel(response.getmProvisionalOffer().get(i).getmLabel());
                    mProvisionalOffer.setmIsAppInsatlled("false");
                    mProvisionalOffer.setmIndex(response.getmProvisionalOffer().get(i).getmIndex());
                    DataBaseQuery.addProductToDataBase(mProvisionalOffer, mContext);
                }
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
