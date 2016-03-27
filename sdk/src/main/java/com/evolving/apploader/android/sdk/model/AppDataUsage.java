package com.evolving.apploader.android.sdk.model;

import android.support.annotation.NonNull;

/**
 */
public class AppDataUsage implements Comparable<AppDataUsage> {
    private String mAppName;
    private String mAppPackageName;
    private int mAppId;
    private Long mMobileData;
    private Long mWifiData;

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

    public Long getmMobileData() {
        return mMobileData;
    }

    public void setmMobileData(Long mMobileData) {
        this.mMobileData = mMobileData;
    }

    public Long getmWifiData() {
        return mWifiData;
    }

    public void setmWifiData(Long mWifiData) {
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
