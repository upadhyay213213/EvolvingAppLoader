package com.evolving.apploader.android.sdk.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.evolving.apploader.android.sdk.api.ProvisionalOfferResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nupadhay on 3/31/2016.
 */
public class PorvisionalOfferGCM implements Parcelable {

    static final Parcelable.Creator<PorvisionalOfferGCM> CREATOR
            = new Parcelable.Creator<PorvisionalOfferGCM>() {
        public PorvisionalOfferGCM createFromParcel(Parcel in) {
            return new PorvisionalOfferGCM(in);
        }

        public PorvisionalOfferGCM[] newArray(int size) {
            return new PorvisionalOfferGCM[size];
        }
    };
    private static final String APPCOUNT = "AppCount";
    private static final String PROVISIONAL_OFFER = "AppList";
    @SerializedName(APPCOUNT)
    private String mResult;
    @SerializedName(PROVISIONAL_OFFER)
    private ArrayList<ProvisonalOfferInnerGCM> mProvisionalOffer;


    public ArrayList<ProvisonalOfferInnerGCM> getmProvisionalOffer() {

        return mProvisionalOffer;
    }

    public PorvisionalOfferGCM(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        mProvisionalOffer = bundle.getParcelableArrayList(PROVISIONAL_OFFER);
        mResult = bundle.getString(APPCOUNT);
    }


    public String getResult() {
        return mResult;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(APPCOUNT, mResult);
        bundle.putParcelableArrayList(PROVISIONAL_OFFER, mProvisionalOffer);
        out.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}