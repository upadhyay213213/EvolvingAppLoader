package com.evolving.apploader.android.sdk.model;

/**
 * Created by nupadhay on 3/31/2016.
 */
public class ProvisionOfferModel {
    private String mType;
    private String mPackage;
    private String mLabel;
    private String mDescription;
    private String mIconUrl;
    private String mUrl;
    private Integer mRating;
    private String mDeveloper;
    private String mIndex;


    public String getmIndex() {
        return mIndex;
    }

    public void setmIndex(String mIndex) {
        this.mIndex = mIndex;
    }

    public String getmIsAppInsatlled() {
        return mIsAppInsatlled;
    }

    public void setmIsAppInsatlled(String mIsAppInsatlled) {
        this.mIsAppInsatlled = mIsAppInsatlled;
    }

    private String mIsAppInsatlled;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDeveloper() {
        return mDeveloper;
    }

    public void setmDeveloper(String mDeveloper) {
        this.mDeveloper = mDeveloper;
    }

    public String getmIconUrl() {
        return mIconUrl;
    }

    public void setmIconUrl(String mIconUrl) {
        this.mIconUrl = mIconUrl;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmPackage() {
        return mPackage;
    }

    public void setmPackage(String mPackage) {
        this.mPackage = mPackage;
    }

    public Integer getmRating() {
        return mRating;
    }

    public void setmRating(Integer mRating) {
        this.mRating = mRating;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

}
