package com.evolving.apploader.android.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.widget.TableRow;

import com.evolving.apploader.android.sdk.datausage.MainActivity;
import com.evolving.apploader.android.sdk.model.AppData;
import com.evolving.apploader.android.sdk.model.AppDataUsage;
import com.evolving.apploader.android.sdk.model.TData;

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

    private static  Map<Integer, AppData> appDataList= new HashMap<Integer, AppData>();
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
        ArrayList<AppDataUsage> appDataUsageHashMap = new ArrayList<>();
        prepareAppDataList(context);
        processStatsFile();
        for (Map.Entry<Integer, AppData> entry : appDataList.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().getTotalData().isEmpty()) {
                AppDataUsage app = new AppDataUsage();
                app.setmAppPackageName(entry.getValue().getAppName());
                app.setmMobileData(entry.getValue().getDataConsumption("mobile"));
                app.setmWifiData(entry.getValue().getDataConsumption("WIFI"));
                appDataUsageHashMap.add(app);
            }
        }
        return appDataUsageHashMap;
    }

    private static  String processStatsFile() {
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
            AppData ad = new AppData();
            ad.setAppName(pm.getApplicationLabel(a).toString());
            ad.setAppID(a.uid);
            if (a.uid >= 10000)
                appDataList.put(a.uid, ad);
            System.out.println("package name:"+a.packageName +" ID:"+a.uid);
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
        AppData a = null;
        String key = getKey(items);

        System.out.println("items:  "+items[3]);
        if (Integer.parseInt(items[3]) > 0) {
            a = appDataList.get(Integer.parseInt(items[3]));
            System.out.println(Integer.parseInt(items[3]));
        }

        if (a != null) {
            System.out.println(a.getAppName());
            System.out.println(Long.parseLong( items[4]));
            if (Long.parseLong( items[4]) == 0) {
                TData backGrData = new TData();
                System.out.println("BG data:"+a.getAppName()+"  data:"+ Long.parseLong( items[5]) +  Long.parseLong( items[7]));
                backGrData.setTotalData(Long.parseLong( items[5]) +  Long.parseLong( items[7]));
                if (a.getTotalData() != null) {
                    if (key != null && key.equals("wifi"))
                        a.getTotalData().put(appInterfaceTypes.WIFIBG.toString(), backGrData.getTotalData());
                    else if (key != null && key.equals("mobile")) {
                        a.getTotalData().put(MainActivity.appInterfaceTypes.MOBBG.toString(), backGrData.getTotalData());
                    }
                }

            } else {
                TData fGrData = new TData();
                fGrData.setRecBytes(items[5]);
                fGrData.setSentBytes(items[7]);
                System.out.println("FG data:"+a.getAppName()+"  data:"+ Long.parseLong( items[5]) +  Long.parseLong( items[7]));
                fGrData.setTotalData(Long.parseLong( items[5]) +  Long.parseLong( items[7]));
                if (a.getTotalData() != null) {
                    if (key != null && key.equals("wifi"))
                        a.getTotalData().put(appInterfaceTypes.WIFIFG.toString(), fGrData.getTotalData());
                    else if (key != null && key.equals("mobile")) {
                        a.getTotalData().put(appInterfaceTypes.MOBFG.toString(), fGrData.getTotalData());
                    }
                }
            }
            appDataList.put(Integer.parseInt(items[3]), a);

        }

    }
    public enum appInterfaceTypes {
        WIFIBG, WIFIFG, MOBBG, MOBFG
    }
}
