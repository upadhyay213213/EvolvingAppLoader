package com.evolving.apploader.android.sdk.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Set;

/**
 * Preferences for the application including user selected options/settings.
 * Contains function to set and get user preference data which is stored using {@link SharedPreferences}
 */
public class SharedPreferenceUtil {
    /**
     * Constants as KEY values for Shared Preferences
     */
    private static final String KEY_APP_BASE_URL = "app_base_url";
    private static final String KEY_AD_DURATION = "ad_duration";
    private static final String KEY_PROJECT_ID = "project_id";
    private static final String KEY_GCM_TOPIC = "gcm_topic";
    private static final String BLANK = "";
    private static final String APP_PACKAGE_NAME = "app_package";
    private static final String IS_FIRST_LAUNCH = "is_first_launch";
    private static final String SCHEDULE_TIME = "schedule_time";

    private static SharedPreferences sharedpreferences;

    public static void setAppBaseUrl(Context context, String url) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KEY_APP_BASE_URL, url);
        editor.apply();
    }

    public static String getAppBaseUrl(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getString(KEY_APP_BASE_URL, BLANK);
    }

    public static void setGCMTopic(Context context, String gcm) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KEY_GCM_TOPIC, gcm);
        editor.apply();
    }

    public static String getGCMTopic(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getString(KEY_GCM_TOPIC, BLANK);
    }

    public static void setProjectId(Context context, String id) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KEY_PROJECT_ID, id);
        editor.apply();
    }

    public static String getProjectId(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getString(KEY_PROJECT_ID, BLANK);
    }

    public static void setAdDuration(Context context, int duration) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(KEY_AD_DURATION, duration);
        editor.apply();
    }

    public static int getAdDuration(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getInt(KEY_AD_DURATION, 50); // todo set default
    }
    public static void setAppPackageName(Context context, Set<String> appName) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putStringSet(APP_PACKAGE_NAME, appName);
        editor.apply();
    }

    public static Set<String> getAppPackageName(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getStringSet(APP_PACKAGE_NAME, Collections.<String>emptySet());
    }

    public static void setIsFirstLaunch(Context context, boolean first) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(IS_FIRST_LAUNCH, first);
        editor.apply();
    }

    public static boolean isFirstLaunch(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public static void setScheduleTime(Context context, long millis) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(SCHEDULE_TIME, millis);
        editor.apply();
    }

    public static long getScheduleTime(Context context) {
        sharedpreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedpreferences.getLong(SCHEDULE_TIME, 24 * 60 * 60 * 1000);
    }
}
