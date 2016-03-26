package com.evolving.apploader.android.sdk;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.evolving.apploader.android.sdk.api.GetConfigRequest;
import com.evolving.apploader.android.sdk.api.NotifyAppCompleteRequest;
import com.evolving.apploader.android.sdk.api.ProvisionalOfferRequest;
import com.evolving.apploader.android.sdk.model.AppDataUsage;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;

import java.util.HashMap;

/**
 * Static methods. Visible to the application that uses this SDK.
 * Application must use only this class to access APIs for all the functions.
] */
public class AppLoaderManager {
    private static RequestQueue mQueue;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        if (mQueue == null) {
            mQueue = new RequestQueue(new DiskBasedCache(context.getCacheDir(), 1024 * 1024), new BasicNetwork(new HurlStack()));
        }
    }

    public static Request requestConfig(String iccid, String imei, Response.Listener listener, Response.ErrorListener errorListener) {
        GetConfigRequest request = new GetConfigRequest.Builder(iccid, imei, System.getProperty("http.agent")).build(listener, errorListener);
        if (mQueue == null) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static Request provisionalOfferRequest(String iccid, String imei, Response.Listener listener, Response.ErrorListener errorListener) {
        ProvisionalOfferRequest request = new ProvisionalOfferRequest.Builder(iccid,imei, System.getProperty("http.agent")).build(listener,errorListener);
        if (mQueue == null) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static Request notifyAppCompleteRequest(String iccid, String imei,String packageID, Response.Listener listener, Response.ErrorListener errorListener) {
        NotifyAppCompleteRequest request = new NotifyAppCompleteRequest.Builder(iccid,imei,packageID, System.getProperty("http.agent")).build(listener,errorListener);
        if (mQueue == null) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static HashMap<String, AppDataUsage> getAppDataUsage() {
        HashMap<String, AppDataUsage> appDataUsageHashMap = new HashMap<>();
        // todo logic
        return appDataUsageHashMap;
    }

    //Getting imsi value from phone
    public static String getIMSI(Context context) {
        return AppLoaderUtil.getIMSI(context);
    }

    //Getting ICCID value from phone
    public static String getICCID(Context context) {
        return AppLoaderUtil.getICCID(context);
    }

    //Getting IMEI value from phone
    public static String getIMEI(Context context) {
        return AppLoaderUtil.getIMEI(context);
    }
}
