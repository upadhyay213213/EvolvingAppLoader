package com.evolving.apploader.android.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.evolving.apploader.android.sdk.api.GetConfigRequest;
import com.evolving.apploader.android.sdk.api.NotifyAppCompleteRequest;
import com.evolving.apploader.android.sdk.api.ProvisionalOfferRequest;
import com.evolving.apploader.android.sdk.database.DataBaseQuery;
import com.evolving.apploader.android.sdk.model.AppDataUsage;
import com.evolving.apploader.android.sdk.model.AppTotalData;
import com.evolving.apploader.android.sdk.model.PorvisionalOfferGCM;
import com.evolving.apploader.android.sdk.model.ProvisionOfferModel;
import com.evolving.apploader.android.sdk.model.ProvisionalOffer;
import com.evolving.apploader.android.sdk.model.ProvisonalOfferInnerGCM;
import com.evolving.apploader.android.sdk.pushnotification.GCMClientManager;
import com.evolving.apploader.android.sdk.services.RequestInitialConfigService;
import com.evolving.apploader.android.sdk.util.AppLoaderConstants;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;
import com.evolving.apploader.android.sdk.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Static methods. Visible to the application that uses this SDK.
 * Application must use only this class to access APIs for all the functions.
] */
public class AppLoaderManager {
    private static RequestQueue mQueue;
    private static Context mContext;

    public static void init(Context context, String url) {
        mContext = context;
        if (!TextUtils.isEmpty(url)) {
            AppLoaderConstants.BASE_URL = url;
        }
        if (mQueue == null) {
              mQueue = Volley.newRequestQueue(mContext);
        }
        if (SharedPreferenceUtil.isFirstLaunch(mContext)) {
            mContext.startService(new Intent(context, RequestInitialConfigService.class));
        }
    }

    public static Request requestConfig(String iccid, String imei, Response.Listener listener, Response.ErrorListener errorListener) {
        HttpsTrustManager.allowAllSSL();
        GetConfigRequest request = new GetConfigRequest.Builder(iccid, imei).build(listener, errorListener);
        if (mQueue == null || TextUtils.isEmpty(AppLoaderConstants.BASE_URL)) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static Request provisionalOfferRequest(String iccid, String imei, Response.Listener listener, Response.ErrorListener errorListener) {
        HttpsTrustManager.allowAllSSL();
        ProvisionalOfferRequest request = new ProvisionalOfferRequest.Builder(iccid, imei).build(SharedPreferenceUtil.getAppBaseUrl(mContext), listener, errorListener);
        if (mQueue == null || TextUtils.isEmpty(AppLoaderConstants.BASE_URL)) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static Request notifyAppCompleteRequest(String iccid, String imei,String packageID, Response.Listener listener, Response.ErrorListener errorListener) {
        HttpsTrustManager.allowAllSSL();
        NotifyAppCompleteRequest request = new NotifyAppCompleteRequest.Builder(iccid, imei, packageID, System.getProperty("http.agent")).build(listener, errorListener);
        if (mQueue == null || TextUtils.isEmpty(AppLoaderConstants.BASE_URL)) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static AppTotalData getTotalAppData() {
        ArrayList<AppDataUsage> appDataUsages = AppLoaderUtil.getAppDataUsage(mContext);
        AppTotalData appTotalData = new AppTotalData();
        appTotalData.setIccid(AppLoaderUtil.getICCID(mContext));
        appTotalData.setImei(AppLoaderUtil.getIMEI(mContext));
        long cellBytes = 0l;
        long wifiBytes = 0l;
        for (AppDataUsage app : appDataUsages) {
            cellBytes += app.getmMobileData();
            wifiBytes += app.getmWifiData();
        }
        ArrayList<AppDataUsage> sorted = new ArrayList<>(appDataUsages);
        Collections.sort(sorted);
        appTotalData.setUsageAppListSorted(sorted.subList(0, 10));
        appTotalData.setTotalCellularBytes(cellBytes);
        appTotalData.setTotalWifiBytes(wifiBytes);
        appTotalData.setLatitude(AppLoaderUtil.getLatlong(mContext).get(0));
        appTotalData.setLongitude(AppLoaderUtil.getLatlong(mContext).get(1));
        AppLoaderUtil.getLatlong(mContext).clear();
        return appTotalData;
    }


    public static ArrayList<ProvisionalOffer> getProvisionalOffer(Context context){
        return DataBaseQuery.getProvisionalOffer(context);
    }

    public static void registerGCM(Context context , String projectID,String gcmTopic){
        GCMClientManager gcmClientManager = new GCMClientManager(context,projectID,gcmTopic);
        gcmClientManager.registerIfNeeded();
    }

    public static void getGCMMessage(String message){

        getResponseFroGCM(message,mContext);
    }

    public static void getResponseFroGCM(String response,Context context) {
        Gson gson = new Gson();
        PorvisionalOfferGCM gCMProvisionalOffer=gson.fromJson(response,PorvisionalOfferGCM.class);
        //Adding count to temp table
        DataBaseQuery.addProductTableCount(Integer.parseInt(gCMProvisionalOffer.getResult()),context);

        //Adding product to temp table
        ArrayList<ProvisonalOfferInnerGCM> provisonalOfferInnerGCMs =gCMProvisionalOffer.getmProvisionalOffer();
        for(int i=0; i<provisonalOfferInnerGCMs.size();i++ ){
            ProvisionOfferModel mProvisionalOffer = new ProvisionOfferModel();
            mProvisionalOffer.setmType(provisonalOfferInnerGCMs.get(i).getmType());
            mProvisionalOffer.setmUrl(provisonalOfferInnerGCMs.get(i).getmURL());
            mProvisionalOffer.setmPackage(provisonalOfferInnerGCMs.get(i).getmPackage());
            mProvisionalOffer.setmIconUrl(provisonalOfferInnerGCMs.get(i).getmIconUrl());
            mProvisionalOffer.setmDescription(provisonalOfferInnerGCMs.get(i).getmDescription());
            mProvisionalOffer.setmRating(provisonalOfferInnerGCMs.get(i).getmRating());
            mProvisionalOffer.setmDeveloper(provisonalOfferInnerGCMs.get(i).getmDeveloper());
            mProvisionalOffer.setmLabel(provisonalOfferInnerGCMs.get(i).getmLabel());
            mProvisionalOffer.setmIsAppInsatlled("false");
            mProvisionalOffer.setmIndex(provisonalOfferInnerGCMs.get(i).getmIndex());
            DataBaseQuery.addProductToTempDataBase(mProvisionalOffer, context);
        }


    }
}
