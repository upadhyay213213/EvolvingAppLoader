package com.evolving.apploader.android.sdk.api;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GetConfigResponse implements Parcelable {

    static final Creator<GetConfigResponse> CREATOR
            = new Creator<GetConfigResponse>() {
        public GetConfigResponse createFromParcel(Parcel in) {
            return new GetConfigResponse(in);
        }

        public GetConfigResponse[] newArray(int size) {
            return new GetConfigResponse[size];
        }
    };

    private static final String APP_AD_DISPLAY_DURATION = "AppAdDisplayDuration";
    private static final String BASE_URL = "BaseURL";
    private static final String GCM_TOPIC = "GCM_TOPIC";
    private static final String PROJECT_ID = "PROJECT_ID";
    private static final String RESULT = "result";

    @SerializedName(APP_AD_DISPLAY_DURATION)
    private int mDuration;
    @SerializedName(BASE_URL)
    private String mUrl;
    @SerializedName(GCM_TOPIC)
    private String mGCMTopic;
    @SerializedName(PROJECT_ID)
    private String mId;
    @SerializedName(RESULT)
    private String mResult;



    public GetConfigResponse(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        mDuration = bundle.getInt(APP_AD_DISPLAY_DURATION);
        mGCMTopic = bundle.getString(GCM_TOPIC);
        mUrl = bundle.getString(BASE_URL);
        mId = bundle.getString(PROJECT_ID);
        mResult = bundle.getString(RESULT);
    }

    public int getAppAdDisplayDuration() {
        return mDuration;
    }

    public String getBaseUrl() {
        return mUrl;
    }

    public String getGCMTopic() {
        return mGCMTopic;
    }

    public String getProjectId() {
        return mId;
    }

    public String getResult() {
        return mResult;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(APP_AD_DISPLAY_DURATION, mDuration);
        bundle.putString(GCM_TOPIC, mGCMTopic);
        bundle.putString(BASE_URL, mUrl);
        bundle.putString(PROJECT_ID, mId);
        bundle.putString(RESULT, mResult);
        out.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
