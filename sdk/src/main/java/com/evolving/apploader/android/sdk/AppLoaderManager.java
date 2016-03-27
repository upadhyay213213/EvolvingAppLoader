package com.evolving.apploader.android.sdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.evolving.apploader.android.sdk.api.GetConfigRequest;
import com.evolving.apploader.android.sdk.api.NotifyAppCompleteRequest;
import com.evolving.apploader.android.sdk.api.ProvisionalOfferRequest;
import com.evolving.apploader.android.sdk.database.DataBaseQuery;
import com.evolving.apploader.android.sdk.model.AppDataUsage;
import com.evolving.apploader.android.sdk.model.ProvisionalOffer;
import com.evolving.apploader.android.sdk.model.AppTotalData;
import com.evolving.apploader.android.sdk.services.RequestInitialConfigService;
import com.evolving.apploader.android.sdk.util.AppLoaderConstants;
import com.evolving.apploader.android.sdk.util.AppLoaderUtil;
import com.evolving.apploader.android.sdk.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Static methods. Visible to the application that uses this SDK.
 * Application must use only this class to access APIs for all the functions.
] */
public class AppLoaderManager {
    private static RequestQueue mQueue;
    private static Context mContext;
    private static AlarmManager mAlarmManager;

    public static void init(Context context, String url) {
        mContext = context;
        if (!TextUtils.isEmpty(url)) {
            AppLoaderConstants.BASE_URL = url;
        }
        if (mQueue == null) {
            mQueue = new RequestQueue(new DiskBasedCache(context.getCacheDir(), 1024 * 1024), new BasicNetwork(new HurlStack()));
        }
        if (SharedPreferenceUtil.isFirstLaunch(mContext)) {
            mContext.startService(new Intent(context, RequestInitialConfigService.class));
            scheduleServiceCall();
        }
    }

    private static void scheduleServiceCall() {
        Calendar cal = Calendar.getInstance();

        Intent intent = new Intent(mContext, RequestInitialConfigService.class);
        intent.putExtra("REPEAT", true);
        PendingIntent pIntent = PendingIntent.getService(mContext, 0, intent, 0);
        if (mAlarmManager != null) {
            mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), SharedPreferenceUtil.getScheduleTime(mContext), pIntent);
        }
    }

    public static Request requestConfig(String iccid, String imei, Response.Listener listener, Response.ErrorListener errorListener) {
        GetConfigRequest request = new GetConfigRequest.Builder(iccid, imei, System.getProperty("http.agent")).build(listener, errorListener);
        if (mQueue == null || TextUtils.isEmpty(AppLoaderConstants.BASE_URL)) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static Request provisionalOfferRequest(String iccid, String imei, Response.Listener listener, Response.ErrorListener errorListener) {
        ProvisionalOfferRequest request = new ProvisionalOfferRequest.Builder(iccid, imei,
                System.getProperty("http.agent")).build(SharedPreferenceUtil.getAppBaseUrl(mContext), listener, errorListener);
        if (mQueue == null || TextUtils.isEmpty(AppLoaderConstants.BASE_URL)) {
            //throw exception
            throw new IllegalStateException("Manager not initialized");
        }
        mQueue.add(request);
        return request;
    }

    public static Request notifyAppCompleteRequest(String iccid, String imei,String packageID, Response.Listener listener, Response.ErrorListener errorListener) {
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
        return appTotalData;
    }


    public static ArrayList<ProvisionalOffer> getProvisionalOffer(Context context){
        ArrayList<ProvisionalOffer> mProvisionalOffer = DataBaseQuery.getProvisionalOffer(context);
        return  mProvisionalOffer;
    }
}
