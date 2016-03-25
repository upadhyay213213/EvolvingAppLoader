package com.evolving.apploader.android.sdk.pushnotification;

import android.util.Log;


public interface GCMRegisterListner {
    void onSuccess(String registrationId, boolean isNewRegistration);
    void onFailure(String ex);
}
