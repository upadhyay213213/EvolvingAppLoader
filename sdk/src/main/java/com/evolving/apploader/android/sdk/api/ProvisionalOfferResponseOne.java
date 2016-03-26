package com.evolving.apploader.android.sdk.api;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nupadhay on 3/23/2016.
 */
public class ProvisionalOfferResponseOne implements Parcelable {

    static final Parcelable.Creator<NotifyAppCompleteResponse> CREATOR
            = new Parcelable.Creator<NotifyAppCompleteResponse>() {
        public NotifyAppCompleteResponse createFromParcel(Parcel in) {
            return new NotifyAppCompleteResponse(in);
        }

        public NotifyAppCompleteResponse[] newArray(int size) {
            return new NotifyAppCompleteResponse[size];
        }
    };
    private static final String RESULT= "result";
    private static final String PROVISIONAL_OFFER= "AppList";
    private String mResult ;


    public ArrayList<ProvisionalOfferResponse> getmProvisionalOffer() {
        return mProvisionalOffer;
    }

    private ArrayList<ProvisionalOfferResponse> mProvisionalOffer;


    public ProvisionalOfferResponseOne(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        mProvisionalOffer = bundle.getParcelableArrayList(PROVISIONAL_OFFER);
        mResult = bundle.getString(RESULT);
    }



    public String getResult() {
        return mResult;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(RESULT, mResult);
        bundle.putParcelableArrayList(PROVISIONAL_OFFER, mProvisionalOffer);
        out.writeBundle(bundle);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}