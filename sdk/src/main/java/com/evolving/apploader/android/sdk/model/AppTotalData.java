package com.evolving.apploader.android.sdk.model;

import java.util.List;

/**
 */
public class AppTotalData {

    private String mIccid;
    private String mImei;
    private long mTotalCellularBytes;
    private long mTotalWifiBytes;
    private double mLatitude;
    private double mLongitude;
    private List<AppDataUsage> mUsageAppList;
    private List<AppDataUsage> mUsageAppListSorted;

    public String getIccid() {
        return mIccid;
    }

    public void setIccid(String mIccid) {
        this.mIccid = mIccid;
    }

    public String getImei() {
        return mImei;
    }

    public void setImei(String mImei) {
        this.mImei = mImei;
    }

    public long getTotalCellularBytes() {
        return mTotalCellularBytes;
    }

    public void setTotalCellularBytes(long mTotalCellularBytes) {
        this.mTotalCellularBytes = mTotalCellularBytes;
    }

    public long getTotalWifiBytes() {
        return mTotalWifiBytes;
    }

    public void setTotalWifiBytes(long mTotalWifiBytes) {
        this.mTotalWifiBytes = mTotalWifiBytes;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public List<AppDataUsage> getUsageAppList() {
        return mUsageAppList;
    }

    public void setUsageAppList(List<AppDataUsage> mUsageAppList) {
        this.mUsageAppList = mUsageAppList;
    }

    public List<AppDataUsage> getUsageAppListSorted() {
        return mUsageAppListSorted;
    }

    public void setUsageAppListSorted(List<AppDataUsage> mUsageAppListSorted) {
        this.mUsageAppListSorted = mUsageAppListSorted;
    }
}
