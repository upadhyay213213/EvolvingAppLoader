package com.evolving.apploader.android.sdk.api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class ProvisionalOfferResponse implements Parcelable {

    static final Creator<GetConfigResponse> CREATOR
            = new Creator<GetConfigResponse>() {
        public GetConfigResponse createFromParcel(Parcel in) {
            return new GetConfigResponse(in);
        }

        public GetConfigResponse[] newArray(int size) {
            return new GetConfigResponse[size];
        }
    };
    private static final String TYPE = "TYPE";
    private static final String PACKAGE = "PACKAGE";
    private static final String LABEL = "LABEL";
    private static final String ICON_URL = "ICON_URL";
    private static final String URL = "URL";
    private static final String RATING = "RATING";
    private static final String DEVELOPER = "DEVELOPER";

    private String mType;
    private String mPackage;

    public String getmDeveloper() {
        return mDeveloper;
    }

    public String getmIconUrl() {
        return mIconUrl;
    }

    public String getmLabel() {
        return mLabel;
    }

    public String getmPackage() {
        return mPackage;
    }

    public Integer getmRating() {
        return mRating;
    }

    public String getmType() {
        return mType;
    }

    public String getmURL() {
        return mURL;
    }

    private String mLabel;
    private String mIconUrl;
    private String mURL;
    private Integer mRating;
    private String mDeveloper;


    public ProvisionalOfferResponse(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        mType = bundle.getString(TYPE);
        mPackage = bundle.getString(PACKAGE);
        mLabel = bundle.getString(LABEL);
        mIconUrl = bundle.getString(ICON_URL);
        mURL = bundle.getString(URL);
        mRating = bundle.getInt(RATING);
        mDeveloper=bundle.getString(DEVELOPER);
    }



    @Override
    public void writeToParcel(Parcel out, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(TYPE, mType);
        bundle.putString(PACKAGE, mPackage);
        bundle.putString(LABEL, mLabel);
        bundle.putString(ICON_URL, mIconUrl);
        bundle.putString(URL, mURL);
        bundle.putString(DEVELOPER, mDeveloper);
        bundle.putInt(RATING,mRating);
        out.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}