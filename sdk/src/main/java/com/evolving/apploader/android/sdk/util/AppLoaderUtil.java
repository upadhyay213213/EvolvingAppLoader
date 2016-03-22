package com.evolving.apploader.android.sdk.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 */
public class AppLoaderUtil {
    //Getting imsi value from phone
    public String getIMSI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSubscriberId();
        return  imsi;
    }
    //Getting ICCID value from phone
    public String getICCID(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSimSerialNumber();
        return  imsi;
    }
    //Getting IMEI value from phone
    public String getIMEI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getDeviceId();
        return  imsi;
    }
}
