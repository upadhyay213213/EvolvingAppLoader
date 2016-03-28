package com.evolving.apploader.android.sdk.api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class NotifyAppCompleteResponse implements Parcelable {

    static final Creator<NotifyAppCompleteResponse> CREATOR
            = new Creator<NotifyAppCompleteResponse>() {
        public NotifyAppCompleteResponse createFromParcel(Parcel in) {
            return new NotifyAppCompleteResponse(in);
        }

        public NotifyAppCompleteResponse[] newArray(int size) {
            return new NotifyAppCompleteResponse[size];
        }
    };
    private static final String RESULT= "result";

    @SerializedName(RESULT)
    private String mResult ;


    public NotifyAppCompleteResponse(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());

        mResult = bundle.getString(RESULT);
    }



    public String getResult() {
        return mResult;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT, mResult);
        out.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}