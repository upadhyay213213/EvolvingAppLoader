package com.evolving.apploader.android.sdk.util;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 */
public class AppLoaderUtil {
    //Getting imsi value from phone
    public static String getIMSI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSubscriberId();
    }
    //Getting ICCID value from phone
    public static String getICCID(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimSerialNumber();
    }
    //Getting IMEI value from phone
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
