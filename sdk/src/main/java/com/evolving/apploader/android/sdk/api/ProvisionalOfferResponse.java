package com.evolving.apploader.android.sdk.api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class ProvisionalOfferResponse implements Parcelable {

    static final Creator<ProvisionalOfferResponse> CREATOR
            = new Creator<ProvisionalOfferResponse>() {
        public ProvisionalOfferResponse createFromParcel(Parcel in) {
            return new ProvisionalOfferResponse(in);
        }

        public ProvisionalOfferResponse[] newArray(int size) {
            return new ProvisionalOfferResponse[size];
        }
    };


    private static final String DESCRIPTION="DESCRIPTION";
    private static final String TYPE = "TYPE";
    private static final String PACKAGE = "PACKAGE";
    private static final String LABEL = "LABEL";
    private static final String ICON_URL = "ICON_URL";
    private static final String URL = "URL";
    private static final String RATING = "RATING";
    private static final String DEVELOPER = "DEVELOPER";
    private static final String INDEX="INDEX";

    @SerializedName(TYPE)
    private String mType;
    @SerializedName(PACKAGE)
    private String mPackage;
    @SerializedName(LABEL)
    private String mLabel;
    @SerializedName(ICON_URL)
    private String mIconUrl;
    @SerializedName(URL)
    private String mURL;
    @SerializedName(RATING)
    private Integer mRating;
    @SerializedName(DEVELOPER)
    private String mDeveloper;
    @SerializedName(DESCRIPTION)
    private String mDescription;
    @SerializedName(INDEX)
    private int mIndex;


    public int getmIndex() {
        return mIndex;
    }
    public String getmDescription() {
        return mDescription;
    }

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


    public ProvisionalOfferResponse(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        mType = bundle.getString(TYPE);
        mPackage = bundle.getString(PACKAGE);
        mLabel = bundle.getString(LABEL);
        mIconUrl = bundle.getString(ICON_URL);
        mURL = bundle.getString(URL);
        mRating = bundle.getInt(RATING);
        mDescription=bundle.getString(DESCRIPTION);
        mDeveloper=bundle.getString(DEVELOPER);
        mIndex= bundle.getInt(INDEX);
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
        bundle.putInt(RATING, mRating);
        bundle.putString(DESCRIPTION, mDescription);
        bundle.putInt(INDEX,mIndex);
        out.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}