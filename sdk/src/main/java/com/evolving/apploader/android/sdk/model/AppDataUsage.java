package com.evolving.apploader.android.sdk.model;

/**
 * Created by vipul on 3/26/2016.
 */
public class AppDataUsage {
    private String mAppName;

    public int getmAppId() {
        return mAppId;
    }

    public void setmAppId(int mAppId) {
        this.mAppId = mAppId;
    }

    public String getmAppName() {
        return mAppName;
    }

    public void setmAppName(String mAppName) {
        this.mAppName = mAppName;
    }

    public String getmAppPackageName() {
        return mAppPackageName;
    }

    public void setmAppPackageName(String mAppPackageName) {
        this.mAppPackageName = mAppPackageName;
    }



    private String mAppPackageName;
    private int mAppId;

    public Long getmMobileData() {
        return mMobileData;
    }

    public void setmMobileData(Long mMobileData) {
        this.mMobileData = mMobileData;
    }

    private Long mMobileData;

    public Long getmWifiData() {
        return mWifiData;
    }

    public void setmWifiData(Long mWifiData) {
        this.mWifiData = mWifiData;
    }

    private Long mWifiData;
}
