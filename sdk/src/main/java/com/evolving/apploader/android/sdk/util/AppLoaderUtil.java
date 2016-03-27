package com.evolving.apploader.android.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.evolving.apploader.android.sdk.model.AppDataUsage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class AppLoaderUtil {

    private static Map<Integer, AppDataUsage> appDataList = new HashMap<>();

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

    public static ArrayList<AppDataUsage> getAppDataUsage(Context context) {
        prepareAppDataList(context);
        processStatsFile();
        return new ArrayList<>(appDataList.values());
    }

    private static String processStatsFile() {
        StringBuilder s = new StringBuilder();
        String line;
        File netFile = new File("/proc/net/xt_qtaguid/stats");
        BufferedReader buffer = null;
        int i=0;

        try {
            buffer = new BufferedReader(new FileReader(netFile));
            while ( ( line = buffer.readLine()) != null) {
                System.out.println(line+i);

                if (line.startsWith("idx"))
                    continue;
                s.append(line+"\n");
                String items [] = line.split(" ");
                if (items.length >= 20)
                    processRecord(items);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                buffer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return s.toString();
    }

    public static void prepareAppDataList (Context context) {
        Context mContext = context.getApplicationContext();
        PackageManager pm = mContext.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo a: packages) {
            AppDataUsage appDataUsage = new AppDataUsage();
            appDataUsage.setAppName(a.name);
            appDataUsage.setmAppPackageName(a.packageName);
            appDataUsage.setAppId(a.uid);
            if (a.uid >= 10000)
                appDataList.put(a.uid, appDataUsage);
            System.out.println("app name = " + a.name + " package name:" + a.packageName + " ID:" + a.uid);
        }
    }

    private static String getKey(String[] items) {
        String key = null;
        if (items[1].contains("wlan")) {
            key = "wifi";
        } else {
            switch (items[1]) {
                case "ccmni":
                case "pdp":
                case "ppp":
                case "rmnet":
                case "usb":
                case "uwbr":
                case "vsnet":
                case "wimax":
                case "rmnet0":
                    key = "mobile";
                    break;
                case "lo":
                case "p2p0":
                case "tun0":
                    break;

                default:
                    System.out.println("invalid interface type");
                    break;
            }
        }
        return key;
    }

    private static void processRecord(String[] items) {
        AppDataUsage a = null;
        String key = getKey(items);

        System.out.println("items:  "+items[3]);
        if (Integer.parseInt(items[3]) > 0) {
            a = appDataList.get(Integer.parseInt(items[3]));
            System.out.println(Integer.parseInt(items[3]));
        }

        if (a != null) {
            System.out.println(a.getAppName());
            System.out.println(Long.parseLong( items[4]));
            if (Long.parseLong(items[4]) == 0) {
                System.out.println("BG data:" + a.getAppName() + "  data:" + Long.parseLong(items[5]) + Long.parseLong(items[7]));
            } else {
                System.out.println("FG data:"+a.getAppName()+"  data:"+ Long.parseLong( items[5]) +  Long.parseLong( items[7]));

            }
            if (key != null && key.equals("wifi"))
                a.setmWifiData(Long.parseLong(items[5]) + Long.parseLong(items[7]));
            else if (key != null && key.equals("mobile")) {
                a.setmMobileData(Long.parseLong(items[5]) + Long.parseLong(items[7]));
            }
            appDataList.put(Integer.parseInt(items[3]), a);

        }

    }
}
