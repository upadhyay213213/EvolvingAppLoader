package com.evolving.apploader.android.sdk.model;

import android.support.annotation.NonNull;

/**
 */
public class AppDataUsage implements Comparable<AppDataUsage> {
    private String mAppName;
    private String mAppPackageName;
    private int mAppId;
    private long mMobileData;
    private long mWifiData;

    public int getAppId() {
        return mAppId;
    }

    public void setAppId(int mAppId) {
        this.mAppId = mAppId;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String mAppName) {
        this.mAppName = mAppName;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public void setmAppPackageName(String mAppPackageName) {
        this.mAppPackageName = mAppPackageName;
    }

    public long getmMobileData() {
        return mMobileData;
    }

    public void setmMobileData(long mMobileData) {
        this.mMobileData = mMobileData;
    }

    public long getmWifiData() {
        return mWifiData;
    }

    public void setmWifiData(long mWifiData) {
        this.mWifiData = mWifiData;
    }

    @Override
    public int compareTo(@NonNull AppDataUsage another) {
        long app1 = getmWifiData() + getmMobileData();
        long app2 = another.getmMobileData() + another.getmWifiData();
        if (app1 == app2) return 0;
        if (app1 > app2) return -1;
        return 1;
    }
}
